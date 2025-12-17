package com.example.springsecurity.repository;

import com.example.springsecurity.constant.Role;
import com.example.springsecurity.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(Role name);

    boolean existsByName(Role name);
}
