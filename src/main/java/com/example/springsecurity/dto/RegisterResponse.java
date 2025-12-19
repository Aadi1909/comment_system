package com.example.springsecurity.dto;


import lombok.Data;

@Data
public class RegisterResponse {

    private String email;
    private String username;
    private String password;

}
