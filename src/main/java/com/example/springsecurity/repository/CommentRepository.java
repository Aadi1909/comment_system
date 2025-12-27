package com.example.springsecurity.repository;

import com.example.springsecurity.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findByUserId(Long userId, Pageable pageable);

    Page<CommentEntity> findByParentCommentIsNull(Pageable pageable);

    List<CommentEntity> findByParentCommentId(Long parentId);
}
