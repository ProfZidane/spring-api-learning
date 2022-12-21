package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") long id) {
        // Implement
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok().body(user.get()); // status 200
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
        // Implement
        System.out.println(user.getName());
        String sha256hex = Hashing.sha256()
                .hashString(user.getPassword(), StandardCharsets.UTF_8)
                .toString();
        user.setPassword(sha256hex);
        User u = userRepository.findByEmail(user.getEmail());
        System.out.println(u);
        if (u != null) {
            System.out.println("Existe dejà !");
            return u;
        } else {
            System.out.println("Existe pas !");
            return userRepository.save(user);
        }

    }



    @PostMapping("/update")
    public User updateUser(Long id, User user) {
        User user1 = userRepository.findById(id).get();
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setImg(user.getImg());

        return userRepository.save(user1);
    }


    @PostMapping("/delete")
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }

}
