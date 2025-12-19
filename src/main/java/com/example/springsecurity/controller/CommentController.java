package com.example.springsecurity.controller;


import com.example.springsecurity.dto.CommentDTO;
import com.example.springsecurity.dto.CommentResponse;
import com.example.springsecurity.dto.PageResponse;
import com.example.springsecurity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/me")
    public ResponseEntity<PageResponse<CommentResponse>> getMyComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<CommentResponse> commentPage =
                commentService.getCommentByUserWithLimit(page, size);

        PageResponse<CommentResponse> response =
                new PageResponse<>(
                        commentPage.getContent(),
                        commentPage.getNumber(),
                        commentPage.getSize(),
                        commentPage.getTotalElements(),
                        commentPage.getTotalPages(),
                        commentPage.isLast()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CommentResponse>> getComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CommentResponse> commentPage =
                commentService.getAllCommentOnAPage(page, size);
        PageResponse<CommentResponse> response =
                new PageResponse<>(
                        commentPage.getContent(),
                        commentPage.getNumber(),
                        commentPage.getSize(),
                        commentPage.getTotalElements(),
                        commentPage.getTotalPages(),
                        commentPage.isLast()
                );

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.saveComment(commentDTO));
    }
}
