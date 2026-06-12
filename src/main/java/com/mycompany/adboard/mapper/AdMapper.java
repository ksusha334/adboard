/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.mapper;

import com.mycompany.adboard.domain.Ad;
import com.mycompany.adboard.dto.AdDto;
import com.mycompany.adboard.entity.AdEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author march
 */
@Component
public class AdMapper {
    
    public AdEntity toEntity(Ad domain) {
        if (domain == null) return null;
        AdEntity entity = new AdEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setImageUrl(domain.getImageUrl());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
    
    public Ad toDomain(AdEntity entity) {
        if (entity == null) return null;
        Ad domain = new Ad();
        domain.setId(entity.getId());
        domain.setTitle(entity.getTitle());
        domain.setDescription(entity.getDescription());
        domain.setPrice(entity.getPrice());
        domain.setImageUrl(entity.getImageUrl());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }
    
    public AdDto toDto(Ad domain) {
        if (domain == null) return null;
        return new AdDto(domain.getId(), domain.getTitle(), domain.getPrice(), domain.getImageUrl());
    }
    
    public Ad toDomain(AdDto dto) {
        if (dto == null) return null;
        Ad domain = new Ad();
        domain.setId(dto.getId());
        domain.setTitle(dto.getTitle());
        domain.setPrice(dto.getPrice());
        domain.setImageUrl(dto.getImageUrl());
        return domain;
    }
    
    public AdDto entityToDto(AdEntity entity) {
        return toDto(toDomain(entity));
    }
}