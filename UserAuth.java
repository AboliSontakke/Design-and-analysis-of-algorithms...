package com.model;

public class UserAuth {
    private boolean isAuthenticated;

    public UserAuth() {
        isAuthenticated = false;
    }

    public boolean authenticate(String username, String password) {
        if ("admin".equals(username) && "password".equals(password)) {
            isAuthenticated = true;
            return true;
        }
        return false; 
    }

    public void logout() {
        isAuthenticated = false;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}
