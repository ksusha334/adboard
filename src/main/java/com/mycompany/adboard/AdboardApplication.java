/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.adboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author march
 */
@SpringBootApplication
public class AdboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdboardApplication.class, args);
        System.out.println("Веб-страница: http://localhost:8080");
    }
}
