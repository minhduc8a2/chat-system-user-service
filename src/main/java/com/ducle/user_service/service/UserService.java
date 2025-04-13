package com.ducle.user_service.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.ducle.user_service.exception.EntityNotExistsException;
import com.ducle.user_service.mapper.UserMapper;
import com.ducle.user_service.model.dto.UserDTO;
import com.ducle.user_service.model.entity.User;
import com.ducle.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${api.users.url}")
    private String baseUrl;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UriComponentsBuilder uriBuilder;

    public boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public URI createUserProfile(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user = userRepository.save(user);
        return uriBuilder.path(baseUrl + "/{id}").buildAndExpand(user.getId()).toUri();

    }

    public UserDTO getUserProfile(Long authId) {
        User user = userRepository.findByAuthId(authId)
                .orElseThrow(() -> new EntityNotExistsException("User not found"));
        return userMapper.userToUserDTO(user);
    }

}
