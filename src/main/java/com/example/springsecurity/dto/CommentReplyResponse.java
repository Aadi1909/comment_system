package com.example.springsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyResponse {
    private Long id;
    private Long parentId;
    private String content;
    private String username;
    private LocalDateTime createdAt;
}
