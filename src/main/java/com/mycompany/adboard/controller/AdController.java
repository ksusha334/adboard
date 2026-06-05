/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.controller;

import com.mycompany.adboard.model.Ad;
import com.mycompany.adboard.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author march
 */
@RestController
@RequestMapping("/api/ads")
public class AdController {
    
    @Autowired
    private AdService adService;
    
    static class AdDto {
        public Long id;
        public String title;
        public Double price;
        public String imageUrl;
        
        public AdDto(Ad ad) {
            this.id = ad.getId();
            this.title = ad.getTitle();
            this.price = ad.getPrice();
            this.imageUrl = ad.getImageUrl();
        }
    }
    
    @GetMapping
    public List<AdDto> getAds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        
        Page<Ad> adPage = adService.getAdsPaginated(page, size);
        
        return adPage.getContent().stream()
                .map(AdDto::new)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public Ad getAdById(@PathVariable Long id) {
        return adService.getAdById(id);
    }
}