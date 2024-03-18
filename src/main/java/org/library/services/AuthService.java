package org.library.services;

import org.library.models.User;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private List<User> users;

    public AuthService() {
        this.users = new ArrayList<>();
        users.add(new User("admin", "password", "admin"));
        users.add(new User("user", "password", "user"));
    }

    /**
     * Attempts to authenticate a user with the provided username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return The authenticated User object if credentials are valid; otherwise, returns {@code null}.
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
     * Creates a new user account with the provided username, password, and role.
     *
     * This method also checks for username uniqueness before creating the account.
     *
     * @param username The desired username for the new account.
     * @param password The password for the new account.
     * @param role The role for the new account, "user" by default.
     * @return {@code true} if the account is successfully created; {@code false} if the username already exists.
     */
    public boolean createUser(String username, String password, String role) {
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(username)) {
                return false;
            }
        }
        users.add(new User(username, password, role));
        return true;
    }
}
