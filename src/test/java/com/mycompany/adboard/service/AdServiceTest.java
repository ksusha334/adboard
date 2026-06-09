/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.service;

import com.mycompany.adboard.model.Ad;
import com.mycompany.adboard.repository.AdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    private Ad testAd;

    @BeforeEach
    void setUp() {
        testAd = new Ad("Тестовое объявление", "Тестовое описание", 5000.0, "test.jpg");
        testAd.setId(1L);
        testAd.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void getAdsPaginated_ShouldReturnPageOfAds() {
        int page = 0;
        int size = 10;
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Ad> ads = List.of(testAd);
        Page<Ad> expectedPage = new PageImpl<>(ads, pageable, 1);

        when(adRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        Page<Ad> result = adService.getAdsPaginated(page, size, "date", "desc");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Тестовое объявление", result.getContent().get(0).getTitle());
        verify(adRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void getAdById_WhenAdExists_ShouldReturnAd() {
        when(adRepository.findById(1L)).thenReturn(Optional.of(testAd));

        Ad result = adService.getAdById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Тестовое объявление", result.getTitle());
        verify(adRepository, times(1)).findById(1L);
    }

    @Test
    void getAdById_WhenAdDoesNotExist_ShouldReturnNull() {
        when(adRepository.findById(99L)).thenReturn(Optional.empty());

        Ad result = adService.getAdById(99L);

        assertNull(result);
        verify(adRepository, times(1)).findById(99L);
    }

    @Test
    void addTestAds_WhenDatabaseIsEmpty_ShouldAdd100Ads() {
        when(adRepository.count()).thenReturn(0L);

        adService.addTestAds();

        verify(adRepository, times(100)).save(any(Ad.class));
    }

    @Test
    void addTestAds_WhenDatabaseIsNotEmpty_ShouldNotAddAds() {
        when(adRepository.count()).thenReturn(50L);

        adService.addTestAds();

        verify(adRepository, never()).save(any(Ad.class));
    }
}
