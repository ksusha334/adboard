/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
/**
 *
 * @author march
 */
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private String role = "USER";
    
    public User() {}
    
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
    
    public Long getId() { 
        return id; 
    }
    public String getUsername() { 
        return username; 
    }
    public String getPassword() { 
        return password; 
    }
    public String getEmail() { 
        return email; 
    }
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    public String getRole() { 
        return role; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    public void setRole(String role) { 
        this.role = role; 
    }
}
