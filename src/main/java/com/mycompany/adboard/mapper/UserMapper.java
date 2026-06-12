/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.mapper;

import com.mycompany.adboard.domain.User;
import com.mycompany.adboard.dto.UserDto;
import com.mycompany.adboard.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author march
 */
@Component
public class UserMapper {
    public UserEntity toEntity(User domain) {
        if (domain == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword());
        entity.setEmail(domain.getEmail());
        entity.setRole(domain.getRole());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
    
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        User domain = new User();
        domain.setId(entity.getId());
        domain.setUsername(entity.getUsername());
        domain.setPassword(entity.getPassword());
        domain.setEmail(entity.getEmail());
        domain.setRole(entity.getRole());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }
    
    public UserDto toDto(User domain) {
        if (domain == null) return null;
        return new UserDto(domain.getId(), domain.getUsername(), domain.getEmail(), domain.getRole());
    }
    
    public User toDomain(UserDto dto) {
        if (dto == null) return null;
        User domain = new User();
        domain.setId(dto.getId());
        domain.setUsername(dto.getUsername());
        domain.setEmail(dto.getEmail());
        domain.setRole(dto.getRole());
        return domain;
    }
    
    public UserDto entityToDto(UserEntity entity) {
        return toDto(toDomain(entity));
    }
}