package com.example.springsecurity.service;

import com.example.springsecurity.dto.CommentDTO;
import com.example.springsecurity.dto.CommentReplyDTO;
import com.example.springsecurity.dto.CommentReplyResponse;
import com.example.springsecurity.dto.CommentResponse;
import com.example.springsecurity.dto.CommentUpdateDto;
import com.example.springsecurity.dto.CommentUpdateResponse;
import com.example.springsecurity.dto.PageResponse;
import com.example.springsecurity.entity.CommentEntity;
import com.example.springsecurity.dto.CommentCreatedResponse;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.CommentRepository;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @CacheEvict(value = { "comments", "myComments" }, allEntries = true)
    public CommentCreatedResponse saveComment(CommentDTO dto, Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommentEntity comment = CommentEntity.builder()
                .content(dto.getContent())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        CommentEntity commentSaved = commentRepository.save(comment);
        CommentCreatedResponse commentCreatedResponse = new CommentCreatedResponse();
        commentCreatedResponse.setCommentId(comment.getId());
        commentCreatedResponse.setMessage("Comment Saved Successfully");

        return commentCreatedResponse;
    }

    @CacheEvict(value = { "comments", "myComments" }, allEntries = true)
    public CommentReplyResponse saveReply(CommentReplyDTO dto, Long userId, Long parentId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommentEntity parent = commentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        CommentEntity reply = CommentEntity.builder()
                .content(dto.getContent())
                .user(user)
                .parentComment(parent)
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(reply);

        return new CommentReplyResponse(
                reply.getId(),
                parentId,
                reply.getContent(),
                user.getUsername(),
                reply.getCreatedAt()
        );
    }

    @Cacheable(value = "myComments", key = "#userId + ':' + #page + ':' + #size")
    public PageResponse<CommentResponse> getMyComments(int page, int size, Long userId) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CommentEntity> pageResult = commentRepository.findByUserId(userId, pageable);

        return PageResponse.fromPage(pageResult.map(this::mapWithReplies));
    }

    @Cacheable(value = "comments", key = "#page + ':' + #size")
    public PageResponse<CommentResponse> getAllRootComments(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CommentEntity> pageResult =
                commentRepository.findByParentCommentIsNull(pageable);

        return PageResponse.fromPage(pageResult.map(this::mapWithReplies));
    }

    private CommentResponse mapWithReplies(CommentEntity entity) {
        return CommentResponse.builder()
                .commentId(entity.getId())
                .content(entity.getContent())
                .authorName(entity.getUser().getUsername())
                .createdAt(entity.getCreatedAt())
                .replies(
                        entity.getReplies()
                                .stream()
                                .map(this::mapWithReplies)
                                .toList()
                )
                .build();
    }

        @CacheEvict(value = { "comments", "myComments" }, allEntries = true)
        public CommentUpdateResponse updateComment(CommentUpdateDto commentUpdateDto, UserDetailsImpl currentUser, Long updateId) {

                CommentEntity commentEntity = commentRepository.findById(updateId)
                                .orElseThrow(() -> new RuntimeException("Comment not found"));

                if (!commentEntity.getUser().getId().equals(currentUser.getId())) {
                        throw new RuntimeException("You can only update your own comments");
                }

                commentEntity.setContent(commentUpdateDto.getContent());
                commentEntity.setUpdatedAt(LocalDateTime.now());
                commentRepository.save(commentEntity);

                CommentUpdateResponse response = new CommentUpdateResponse();
                response.setUpdatedCommentContent(commentUpdateDto.getContent());
                return response;
        }
}

