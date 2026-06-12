/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.service;

import com.mycompany.adboard.domain.Ad;
import com.mycompany.adboard.dto.AdDto;
import com.mycompany.adboard.entity.AdEntity;
import com.mycompany.adboard.mapper.AdMapper;
import com.mycompany.adboard.repository.AdRepository;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author march
 */
@Service
public class AdService {
    
    private static final String SORT_FIELD_CREATED_AT = "createdAt";
    private static final String SORT_FIELD_PRICE = "price";
    private static final String SORT_ORDER_DESC = "desc";
    private static final String CACHE_KEY_SEPARATOR = ":";
    
    @Autowired
    private AdRepository adRepository;
    
    @Autowired
    private AdMapper adMapper;
    
    private final ConcurrentHashMap<String, Page<AdEntity>> pageCache = new ConcurrentHashMap<>();
    
    public List<AdDto> getAdsDto(int page, int size, String sortBy, String order) {
        Page<AdEntity> entityPage = getAdsPaginated(page, size, sortBy, order);
        return entityPage.getContent().stream()
            .map(adMapper::entityToDto)
            .collect(Collectors.toList());
    }
    
    private Page<AdEntity> getAdsPaginated(int page, int size, String sortBy, String order) {
        String cacheKey = page + CACHE_KEY_SEPARATOR + size 
                + CACHE_KEY_SEPARATOR + sortBy 
                + CACHE_KEY_SEPARATOR + order;
        
        if (pageCache.containsKey(cacheKey)) {
            return pageCache.get(cacheKey);
        }
        
        String sortField = SORT_FIELD_PRICE.equals(sortBy) ? SORT_FIELD_PRICE : SORT_FIELD_CREATED_AT;
        Sort.Direction direction = SORT_ORDER_DESC.equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField).and(Sort.by(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<AdEntity> result = adRepository.findAll(pageable);
        pageCache.put(cacheKey, result);
        
        return result;
    }
    
    public Ad getAdById(Long id) {
        AdEntity entity = adRepository.findById(id).orElse(null);
        return adMapper.toDomain(entity);
    }
    
    public AdDto getAdDtoById(Long id) {
        Ad domain = getAdById(id);
        return adMapper.toDto(domain);
    }
    
    public void addTestAds() {
        if (adRepository.count() == 0) {
            for (int i = 1; i <= 100; i++) {
                String imageUrl = "https://picsum.photos/300/200?random=" + i;
                AdEntity ad = new AdEntity(
                    "Объявление " + i,
                    "Полное описание объявления номер " + i,
                    1000.0 + (i * 100) % 50000,
                    imageUrl
                );
                adRepository.save(ad);
            }
            System.out.println("Добавлено 100 тестовых объявлений");
        }
    }
}