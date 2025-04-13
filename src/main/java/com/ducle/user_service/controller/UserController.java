package com.ducle.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ducle.user_service.model.dto.EmailCheckingRequest;
import com.ducle.user_service.model.dto.UserDTO;
import com.ducle.user_service.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("${api.users.url}")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/email_exists")
    public ResponseEntity<Boolean> checkEmailExists(@Valid @RequestBody EmailCheckingRequest request) {
        return ResponseEntity.ok(false);
    }

    @PostMapping
    public ResponseEntity<Void> createUserProfile(@Valid @RequestBody UserDTO userDTO,
            UriComponentsBuilder uriBuilder) {
        return ResponseEntity.created(userService.createUserProfile(userDTO)).build();
    }

    @GetMapping("/{authId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long authId) {
        return ResponseEntity.ok(userService.getUserProfile(authId));
    }

}
