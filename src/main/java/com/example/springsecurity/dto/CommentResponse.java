package com.example.springsecurity.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
    private List<CommentResponse> replies;
}
