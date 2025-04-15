package com.ducle.user_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.ducle.user_service.mapper.UserMapper;
import com.ducle.user_service.model.dto.EmailCheckingRequest;
import com.ducle.user_service.model.dto.UserDTO;
import com.ducle.user_service.model.entity.User;
import com.ducle.user_service.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UriComponentsBuilder uriBuilder;

    @Mock
    private UriComponentsBuilder uriBuilderWithPath;

    @Mock
    private UriComponents uriComponents;

    private static final String BASE_URL = "/api/v1/users";
    private static final String USER_EMAIL = "user@example.com";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "baseUrl", BASE_URL);
    }

    @Test
    void testCheckEmailExists() {
        when(userRepository.existsByEmail(USER_EMAIL)).thenReturn(true);
        boolean result = userService.checkEmailExists(new EmailCheckingRequest(USER_EMAIL));
        assertTrue(result, "Email should exist in the database");
    }

    @Test
    void testCreateUserProfile() {
        long authId = 1L;
        URI mockUri = URI.create("http://localhost:8080/api/v1/users/1");
        UserDTO userDTO = new UserDTO(USER_EMAIL, authId);
        User user = new User(1L, USER_EMAIL, authId);
        when(userMapper.userDTOToUser(userDTO)).thenReturn(new User(USER_EMAIL, authId));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(uriBuilder.path(BASE_URL + "/{id}")).thenReturn(uriBuilderWithPath);
        when(uriBuilderWithPath.buildAndExpand(user.getId())).thenReturn(uriComponents);
        when(uriComponents.toUri()).thenReturn(mockUri);
        URI uri = userService.createUserProfile(userDTO);
        assertTrue(uri.toString().contains(BASE_URL + "/" + user.getId()), "URI should contain the user ID");
    }

    @Test
    void testGetUserProfile() {
        long authId = 1L;
        User user = new User(1L, USER_EMAIL, authId);
        UserDTO userDTO = new UserDTO(USER_EMAIL, authId);
        when(userRepository.findByAuthId(authId)).thenReturn(java.util.Optional.of(user));
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);
        UserDTO result = userService.getUserProfile(authId);
        assertEquals(USER_EMAIL, result.email(), "User email should match the expected email");
    }

}
