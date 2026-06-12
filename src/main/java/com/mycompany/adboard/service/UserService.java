/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.service;

import com.mycompany.adboard.domain.User;
import com.mycompany.adboard.dto.UserDto;
import com.mycompany.adboard.entity.UserEntity;
import com.mycompany.adboard.mapper.UserMapper;
import com.mycompany.adboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
/**
 *
 * @author march
 */
@Service
public class UserService {
    
    private static final int MIN_PASSWORD_LENGTH = 4;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public boolean registerUser(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return false;
        }
        
        String encodedPassword = passwordEncoder.encode(password);
        UserEntity entity = new UserEntity(username, encodedPassword, email, "USER");
        userRepository.save(entity);
        return true;
    }
    
    public User findByUsername(String username) {
        UserEntity entity = userRepository.findByUsername(username).orElse(null);
        return userMapper.toDomain(entity);
    }
    
    public UserDto findUserDtoByUsername(String username) {
        User domain = findByUsername(username);
        return userMapper.toDto(domain);
    }
    
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}