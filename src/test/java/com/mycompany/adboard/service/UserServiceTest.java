/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.service;

import com.mycompany.adboard.entity.UserEntity;
import com.mycompany.adboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 *
 * @author march
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity testUserEntity;

    @BeforeEach
    void setUp() {
        testUserEntity = new UserEntity("testuser", "encodedPassword", "test@example.com", "USER");
        testUserEntity.setId(1L);
    }

    @Test
    void registerUser_WhenUsernameIsFree_ShouldReturnTrue() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUserEntity);

        boolean result = userService.registerUser("newuser", "password123", "new@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void registerUser_WhenUsernameExists_ShouldReturnFalse() {
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        boolean result = userService.registerUser("existinguser", "password123", "test@example.com");

        assertFalse(result);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void registerUser_WhenPasswordTooShort_ShouldReturnFalse() {
        boolean result = userService.registerUser("newuser", "123", "new@example.com");

        assertFalse(result);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void checkPassword_WithCorrectPassword_ShouldReturnTrue() {
        String rawPassword = "password123";
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);

        boolean result = userService.checkPassword(rawPassword, encodedPassword);

        assertTrue(result);
    }

    @Test
    void checkPassword_WithWrongPassword_ShouldReturnFalse() {
        String rawPassword = "password123";
        String encodedPassword = new BCryptPasswordEncoder().encode("different");

        boolean result = userService.checkPassword(rawPassword, encodedPassword);

        assertFalse(result);
    }
}