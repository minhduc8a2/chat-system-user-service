package com.ducle.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ducle.user_service.model.dto.ClientUserDTO;
import com.ducle.user_service.model.dto.EmailCheckingRequest;
import com.ducle.user_service.model.dto.UserDTO;
import com.ducle.user_service.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("${api.users.url}")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/email_exists")
    public ResponseEntity<Boolean> checkEmailExists(@Valid @RequestBody EmailCheckingRequest request) {
        return ResponseEntity.ok(userService.checkEmailExists(request));
    }

    @PostMapping
    public ResponseEntity<Void> createUserProfile(@Valid @RequestBody UserDTO userDTO,
            UriComponentsBuilder uriBuilder) {
        return ResponseEntity.created(userService.createUserProfile(userDTO)).build();
    }

    @GetMapping("/{authId}")
    public ResponseEntity<ClientUserDTO> getUserProfile(@Min(1) @PathVariable Long authId) {
        return ResponseEntity.ok(userService.getUserProfile(authId));
    }

    @PatchMapping("/{authId}")
    public ResponseEntity<ClientUserDTO> updateUserProfile(@Min(1) @PathVariable Long authId,
            @Valid @RequestBody ClientUserDTO clientUserDTO) {
        return ResponseEntity.ok(userService.updateUserProfile(authId, clientUserDTO));
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortBy, sortDir));
    }

}
