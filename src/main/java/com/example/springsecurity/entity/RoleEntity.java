package com.example.springsecurity.entity;


import com.example.springsecurity.constant.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Role name;

}
