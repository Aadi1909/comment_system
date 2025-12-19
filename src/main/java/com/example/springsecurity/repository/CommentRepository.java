package com.example.springsecurity.repository;

import com.example.springsecurity.dto.CommentDTO;
import com.example.springsecurity.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    @EntityGraph(attributePaths = "user")
    Page<CommentEntity> findAll(Pageable pageable);

    Page<CommentEntity> findByUserId(Long userId, Pageable pageable );

}
