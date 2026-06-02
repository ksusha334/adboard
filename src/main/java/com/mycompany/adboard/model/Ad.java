/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 *
 * @author march
 */
@Entity
@Table(name = "ads")
public class Ad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private LocalDateTime createdAt;
    
    public Ad() {}
    
    public Ad(String title, String description, Double price, String imageUrl) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(Double price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}