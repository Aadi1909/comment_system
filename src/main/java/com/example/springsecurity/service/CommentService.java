package com.example.springsecurity.service;


import com.example.springsecurity.dto.CommentDTO;
import com.example.springsecurity.dto.CommentResponse;
import com.example.springsecurity.entity.CommentEntity;
import com.example.springsecurity.dto.CommentCreatedResponse;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.CommentRepository;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentCreatedResponse saveComment(CommentDTO commentDTO) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails =
                (UserDetailsImpl) authentication.getPrincipal();

        UserEntity userEntity = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setUser(userEntity);
        commentEntity.setCreatedAt(LocalDateTime.now());

        commentRepository.save(commentEntity);

        return new CommentCreatedResponse("Comment saved successfully");
    }

    public Page<CommentResponse> getCommentByUserWithLimit(int page, int size) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails =
                (UserDetailsImpl) authentication.getPrincipal();

        Pageable pageable =
                PageRequest.of(page, size, Sort.by("createdAt").descending());

        return commentRepository
                .findByUserId(userDetails.getId(), pageable)
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUser().getUsername()
                ));
    }

    public Page<CommentResponse> getAllCommentOnAPage(int page, int size) {
        Pageable pageable =
                PageRequest.of(page, size, Sort.by("createdAt").descending());
        return commentRepository
                .findAll(pageable)
                .map(entity -> CommentResponse.builder()
                        .commentId(entity.getId())
                        .content(entity.getContent())
                        .createdAt(entity.getCreatedAt())
                        .authorName(entity.getUser().getUsername())
                        .build()
                );
    }
}
