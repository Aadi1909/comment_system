//package com.example.springsecurity.dumb;
//
//import com.example.springsecurity.constant.Role;
//import com.example.springsecurity.entity.RoleEntity;
//import com.example.springsecurity.entity.UserEntity;
//import com.example.springsecurity.repository.RoleRepository;
//import com.example.springsecurity.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) {
//
//        RoleEntity userRole = new RoleEntity();
//        RoleEntity adminRole = new RoleEntity();
//        userRole.setName(Role.ROLE_USER);
//        adminRole.setName(Role.ROLE_ADMIN);
//
//        userRole = roleRepository.save(userRole);
//        adminRole = roleRepository.save(adminRole);
//
//        UserEntity user = new UserEntity();
//        user.setEmail("admin");
//        user.setPassword(passwordEncoder.encode("1234"));
//        user.setEnabled(true);
//        user.setRoles(Set.of(userRole));
//
//        UserEntity user2 = new UserEntity();
//        user2.setEmail("admin2");
//        user2.setPassword(passwordEncoder.encode("1234"));
//        user2.setEnabled(true);
//        user2.setRoles(Set.of(adminRole));
//
//        userRepository.save(user2);
//        userRepository.save(user);
//    }
//}
