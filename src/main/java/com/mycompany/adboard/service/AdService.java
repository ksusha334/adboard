/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.service;

import com.mycompany.adboard.model.Ad;
import com.mycompany.adboard.repository.AdRepository;
import java.util.concurrent.ConcurrentHashMap;
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
    
    @Autowired
    private AdRepository adRepository;
    
    private final ConcurrentHashMap<String, Page<Ad>> pageCache = new ConcurrentHashMap<>();
    
    public Page<Ad> getAdsPaginated(int page, int size, String sortBy, String order) {
        String cacheKey = page + ":" + size + ":" + sortBy + ":" + order;
        
        if (pageCache.containsKey(cacheKey)) {
            System.out.println("Возвращаем страницу " + page + " из кэша");
            return pageCache.get(cacheKey);
        }
        
        System.out.println("Загружаем страницу " + page + " из базы данных");
        String sortField = "price".equals(sortBy) ? "price" : "createdAt";
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        Sort sort = Sort.by(direction, sortField).and(Sort.by(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Ad> result = adRepository.findAll(pageable);
        pageCache.put(cacheKey, result);

        return result;
    }
    
    public Ad getAdById(Long id) {
        return adRepository.findById(id).orElse(null);
    }
    
    public void addTestAds() {
        if (adRepository.count() == 0) {
            for (int i = 1; i <= 100; i++) {
                String imageUrl = "https://picsum.photos/300/200?random=" + i;
                Ad ad = new Ad(
                    "Объявление " + i,
                    "Полное описание объявления номер " + i + ". Здесь может быть любой текст.", 
                    1000.0 + (i * 100) % 50000,
                    imageUrl
                );
                adRepository.save(ad);
            }
            System.out.println("Добавлено 100 тестовых объявлений с картинками");
        }
    }
    
    public void clearCache() {
        pageCache.clear();
        System.out.println("Кэш очищен");
    }
}
