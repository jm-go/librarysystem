package org.library.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.library.models.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private List<User> users;
    private final String filePath = "users.json";

    public AuthService() {
        this.users = new ArrayList<>();
        loadUsersFromJson();
        if (users.isEmpty()) {
            users.add(new User("admin", "password", "admin"));
            users.add(new User("user", "password", "user"));
            saveUsersToJson();
        }
    }

    /**
     * Loads users from a JSON file into the users list.
     */
    private void loadUsersFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                users = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            }

        } catch (Exception e) {
            System.err.println("Could not load users from file: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Saves the current list of users to a JSON file.
     */
    public void saveUsersToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File("users.json"), users);
            System.out.println("SYSTEM MESSAGE: Users saved successfully to users.json");
        } catch (IOException e) {
            System.err.println("Error saving users to file: users.json");
            e.printStackTrace();
        }
    }

    /**
     * Authenticates a user based on username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The User object if authentication is successful, otherwise null.
     */
    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Creates a new user with a unique username.
     *
     * @param username The desired username for the new account.
     * @param password The password for the new account.
     * @param role The role for the new account. Defaults to "user".
     * @return True if the account is created successfully, false if the username exists.
     */
    public boolean createUser(String username, String password, String role) {
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(username)) {
                return false;
            }
        }
        users.add(new User(username, password, role));
        saveUsersToJson();
        return true;
    }

    /**
     * Retrieves a user by username.
     *
     * @param username The username of the desired user.
     * @return The User object if found, otherwise null.
     */
    public User getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
