/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.dto;

/**
 *
 * @author march
 */
public class AdDto {
    private Long id;
    private String title;
    private Double price;
    private String imageUrl;
    
    public AdDto() {}
    
    public AdDto(Long id, String title, Double price, String imageUrl) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
    }
    
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getTitle() { 
        return title; 
    }
    public void setTitle(String title) { 
        this.title = title;
    }
    
    public Double getPrice() { 
        return price; 
    }
    public void setPrice(Double price) { 
        this.price = price; 
    }
    
    public String getImageUrl() {
        return imageUrl; 
    }
    public void setImageUrl(String imageUrl) { 
        this.imageUrl = imageUrl; 
    }
}