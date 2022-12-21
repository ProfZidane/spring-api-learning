package com.example.demo.controllers;


import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserRepository userRepository;



    @PostMapping("/login")
    public ResponseEntity<User> login(@Validated @RequestBody User user) {
        User user1 = userRepository.getUserByEmail(user.getEmail());
        if (user1 != null) {
            String sha256hex = Hashing.sha256()
                    .hashString(user.getPassword(), StandardCharsets.UTF_8)
                    .toString();
            if (user1.getPassword() == sha256hex) {
                return ResponseEntity.ok().body(user1);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return null;
    }
}
