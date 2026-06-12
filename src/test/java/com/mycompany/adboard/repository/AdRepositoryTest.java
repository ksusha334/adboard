/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.repository;

import com.mycompany.adboard.entity.AdEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author march
 */
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdRepositoryTest {

    @Autowired
    private AdRepository adRepository;

    private AdEntity ad1;
    private AdEntity ad2;
    private AdEntity ad3;

    @BeforeEach
    void setUp() {
        adRepository.deleteAll();

        ad1 = new AdEntity("Объявление A", "Описание A", 1000.0, "image1.jpg");
        ad1.setCreatedAt(LocalDateTime.of(2024, 1, 1, 10, 0));
        
        ad2 = new AdEntity("Объявление B", "Описание B", 2000.0, "image2.jpg");
        ad2.setCreatedAt(LocalDateTime.of(2024, 1, 2, 10, 0));
        
        ad3 = new AdEntity("Объявление C", "Описание C", 3000.0, "image3.jpg");
        ad3.setCreatedAt(LocalDateTime.of(2024, 1, 3, 10, 0));

        adRepository.saveAll(List.of(ad1, ad2, ad3));
    }

    @Test
    void findAll_ShouldReturnAllAds() {
        List<AdEntity> ads = adRepository.findAll();
        assertEquals(3, ads.size());
    }

    @Test
    void findById_ShouldReturnCorrectAd() {
        AdEntity found = adRepository.findById(ad2.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Объявление B", found.getTitle());
        assertEquals(2000.0, found.getPrice());
    }

    @Test
    void save_ShouldPersistAd() {
        AdEntity newAd = new AdEntity("Новое объявление", "Новое описание", 5000.0, "new.jpg");
        AdEntity saved = adRepository.save(newAd);
        assertNotNull(saved.getId());
        assertEquals("Новое объявление", saved.getTitle());
    }

    @Test
    void delete_ShouldRemoveAd() {
        adRepository.deleteById(ad1.getId());
        List<AdEntity> ads = adRepository.findAll();
        assertEquals(2, ads.size());
    }

    @Test
    void findAll_WithPagination_ShouldReturnPage() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AdEntity> page = adRepository.findAll(pageable);
        assertEquals(2, page.getContent().size());
        assertEquals(3, page.getTotalElements());
        assertEquals("Объявление C", page.getContent().get(0).getTitle());
    }
    
    @Test
    void findByTitleContainingIgnoreCase_ShouldReturnMatchingAds() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<AdEntity> result = adRepository.findByTitleContainingIgnoreCase("B", pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("Объявление B", result.getContent().get(0).getTitle());
    }
}