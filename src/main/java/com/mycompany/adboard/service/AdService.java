/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.service;

import com.mycompany.adboard.model.Ad;
import com.mycompany.adboard.repository.AdRepository;
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
    
    public Page<Ad> getAdsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return adRepository.findAll(pageable);
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
}
