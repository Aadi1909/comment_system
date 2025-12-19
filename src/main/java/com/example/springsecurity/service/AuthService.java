package com.example.springsecurity.service;

import com.example.springsecurity.constant.Role;
import com.example.springsecurity.dto.RegisterRequest;
import com.example.springsecurity.dto.RegisterResponse;
import com.example.springsecurity.entity.RoleEntity;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setUsername(request.getUsername());

        for (Role role : request.getRoles()) {
            RoleEntity roleEntity = roleRepository
                    .findByName(role)
                    .orElseGet(() -> {
                        RoleEntity newRole = new RoleEntity();
                        newRole.setName(role);
                        return roleRepository.save(newRole);
                    });

            user.getRoles().add(roleEntity);
        }

        userRepository.save(user);
        RegisterResponse response = new RegisterResponse();
        response.setUsername(request.getUsername());
        response.setEmail(request.getEmail());
        response.setPassword(request.getPassword());
        return response;
    }
}

