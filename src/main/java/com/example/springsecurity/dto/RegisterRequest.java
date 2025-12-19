package com.example.springsecurity.dto;

import com.example.springsecurity.constant.Role;
import java.util.Set;

public class RegisterRequest {

    private String email;
    private String password;
    private Set<Role> roles;
    private String username;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }
}
