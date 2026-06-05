/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.controller;

import com.mycompany.adboard.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author march
 */
@Controller
public class HomeController {
    
    @Autowired
    private AdService adService;
    
    @GetMapping("/")
    public String home() {
        adService.addTestAds();
        return "home";
    }
    
    @GetMapping("/ad/{id}")
    public String adPage() {
        return "ad";
    }
}