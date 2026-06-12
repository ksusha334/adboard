/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.repository;

import com.mycompany.adboard.entity.AdEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author march
 */
@Repository
public interface AdRepository extends JpaRepository<AdEntity, Long> {
    Page<AdEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}