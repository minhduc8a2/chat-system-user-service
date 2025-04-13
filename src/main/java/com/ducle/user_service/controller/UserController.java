package com.ducle.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ducle.user_service.model.dto.CreateProfileRequest;
import com.ducle.user_service.model.dto.EmailCheckingRequest;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.users.url}")
public class UserController {
    @Value("${api.users.url}")
    private String baseUrl;

    @PostMapping("/email_exists")
    public ResponseEntity<Boolean> checkEmailExists(@Valid @RequestBody EmailCheckingRequest request) {
        return ResponseEntity.ok(false);
    }

    @PostMapping
    public ResponseEntity<Void> createUserProfile(@Valid @RequestBody CreateProfileRequest createProfileRequest,
            UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path(baseUrl+"/{id}").buildAndExpand(1).toUri();
        return ResponseEntity.created(uri).build();
    }

}
