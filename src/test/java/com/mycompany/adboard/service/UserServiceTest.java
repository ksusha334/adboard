/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.service;

import com.mycompany.adboard.model.User;
import com.mycompany.adboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

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

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("testuser", new BCryptPasswordEncoder().encode("password123"), "test@example.com");
        testUser.setId(1L);
    }

    @Test
    void registerUser_WhenUsernameIsFree_ShouldReturnTrue() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        boolean result = userService.registerUser("newuser", "password123", "new@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_WhenUsernameAlreadyExists_ShouldReturnFalse() {
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        boolean result = userService.registerUser("existinguser", "password123", "test@example.com");

        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findByUsername_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        User result = userService.findByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void findByUsername_WhenUserDoesNotExist_ShouldReturnNull() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        User result = userService.findByUsername("unknown");

        assertNull(result);
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