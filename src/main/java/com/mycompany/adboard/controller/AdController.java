/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.controller;

import com.mycompany.adboard.dto.AdDto;
import com.mycompany.adboard.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 *
 * @author march
 */
@RestController
@RequestMapping("/api/ads")
public class AdController {
    
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 9;
    private static final String DEFAULT_SORT_BY = "date";
    private static final String DEFAULT_SORT_ORDER = "desc";
    
    @Autowired
    private AdService adService;
    
    @GetMapping
    public List<AdDto> getAds(
            @RequestParam(defaultValue = DEFAULT_PAGE + "") int page,
            @RequestParam(defaultValue = DEFAULT_SIZE + "") int size,
            @RequestParam(defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = DEFAULT_SORT_ORDER) String order) {
        
        return adService.getAdsDto(page, size, sortBy, order);
    }
    
    @GetMapping("/{id}")
    public AdDto getAdById(@PathVariable Long id) {
        return adService.getAdDtoById(id);
    }
}