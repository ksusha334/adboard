/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.service;

import com.mycompany.adboard.entity.AdEntity;
import com.mycompany.adboard.repository.AdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author march
 */
@ExtendWith(MockitoExtension.class)
class AdServiceTest {

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private AdService adService;

    private AdEntity testAdEntity;

    @BeforeEach
    void setUp() {
        testAdEntity = new AdEntity("Тестовое объявление", "Тестовое описание", 5000.0, "test.jpg");
        testAdEntity.setId(1L);
        testAdEntity.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void addTestAds_WhenDatabaseIsEmpty_ShouldAdd100Ads() {
        when(adRepository.count()).thenReturn(0L);

        adService.addTestAds();

        verify(adRepository, times(100)).save(any(AdEntity.class));
    }

    @Test
    void addTestAds_WhenDatabaseIsNotEmpty_ShouldNotAddAds() {
        when(adRepository.count()).thenReturn(50L);

        adService.addTestAds();

        verify(adRepository, never()).save(any(AdEntity.class));
    }
}