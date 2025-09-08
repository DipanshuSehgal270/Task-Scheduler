package com.example.userService.controller;

import com.example.userService.dto.UserResponse;
import com.example.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setId(user.getId());
                    userResponse.setUsername(user.getUsername());
                    userResponse.setEmail(user.getEmail());
                    return ResponseEntity.ok(userResponse);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}