package com.example.springsecurity.dto;

public class CommentCreatedResponse {
    private String message;
    public String getMessage() {
        return message;
    }
    public CommentCreatedResponse(String message) {
        this.message = message;
    }
}
