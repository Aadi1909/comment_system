package com.example.springsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class CommentDTO {


    @NotBlank(message = "Comment content cannot be empty")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
