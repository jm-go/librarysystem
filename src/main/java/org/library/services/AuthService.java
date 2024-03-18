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

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

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
