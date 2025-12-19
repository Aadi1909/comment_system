package com.example.springsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
@Getter
public class CommentResponse {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private String authorName;
}
