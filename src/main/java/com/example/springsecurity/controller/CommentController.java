package com.example.springsecurity.controller;


import com.example.springsecurity.dto.CommentDTO;
import com.example.springsecurity.dto.CommentReplyDTO;
import com.example.springsecurity.dto.CommentResponse;
import com.example.springsecurity.dto.CommentUpdateDto;
import com.example.springsecurity.dto.PageResponse;
import com.example.springsecurity.service.CommentService;
import com.example.springsecurity.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<PageResponse<CommentResponse>> getComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(commentService.getAllRootComments(page, size));
    }

    @GetMapping("/me")
    public ResponseEntity<PageResponse<CommentResponse>> myComments(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(commentService.getMyComments(page, size, user.getId()));
    }

    @PostMapping
    public ResponseEntity<?> createComment(
            Authentication authentication,
            @RequestBody CommentDTO dto
    ) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(commentService.saveComment(dto, user.getId()));
    }

    @PostMapping("/reply")
    public ResponseEntity<?> createReply(
            Authentication authentication,
            @RequestParam Long parentId,
            @RequestBody CommentReplyDTO dto
    ) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(
                commentService.saveReply(dto, user.getId(), parentId)
        );
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateComment(
        Authentication authentication,
        @RequestParam Long updateId,
        @RequestBody CommentUpdateDto CommentUpdateDto
    ) {
        return ResponseEntity.ok(
            commentService.updateComment(CommentUpdateDto, (UserDetailsImpl) authentication.getPrincipal(), updateId)
        );
    }

}

