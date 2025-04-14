package com.ducle.user_service.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.ducle.user_service.exception.EntityNotExistsException;
import com.ducle.user_service.mapper.UserMapper;
import com.ducle.user_service.model.dto.EmailCheckingRequest;
import com.ducle.user_service.model.dto.UserDTO;
import com.ducle.user_service.model.entity.User;
import com.ducle.user_service.model.enums.UserSortField;
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

    public boolean checkEmailExists(EmailCheckingRequest request) {
        return userRepository.existsByEmail(request.email());
    }

    @Transactional
    public URI createUserProfile(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        User savedUser = userRepository.save(user);
        return uriBuilder.path(baseUrl + "/{id}").buildAndExpand(savedUser.getId()).toUri();

    }

    public UserDTO getUserProfile(Long authId) {
        User user = userRepository.findByAuthId(authId)
                .orElseThrow(() -> new EntityNotExistsException("User not found"));
        return userMapper.userToUserDTO(user);
    }

    public Page<UserDTO> getAllUsers(int page, int size, String sortBy, String sortDir) {
        UserSortField sortField;
        try {
            sortField = UserSortField.valueOf(sortBy);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid sortBy value: " + sortBy);
        }
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortField.name()).descending()
                : Sort.by(sortField.name()).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable).map(userMapper::userToUserDTO);
    }
}
