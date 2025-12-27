package com.example.springsecurity.dto;

import com.example.springsecurity.constant.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("role")
    public void setRole(Role role) {
        this.roles = Set.of(role);
    }

    public String getUsername() {
        return username;
    }
}
