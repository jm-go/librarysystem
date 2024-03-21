package org.library.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String role;
    private List<Integer> loanedBookNumbers;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.loanedBookNumbers = new ArrayList<>();
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Integer> getLoanedBookNumbers() {
        return loanedBookNumbers;
    }

    public void setLoanedBookNumbers(List<Integer> loanedBookNumbers) {
        this.loanedBookNumbers = loanedBookNumbers;
    }

    public void addLoanedBookNumber(int bookNumber) {
        if (!this.loanedBookNumbers.contains(bookNumber)) {
            this.loanedBookNumbers.add(bookNumber);
        }
    }

    public void removeLoanedBookNumber(int bookNumber) {
        this.loanedBookNumbers.remove(Integer.valueOf(bookNumber));
    }
}