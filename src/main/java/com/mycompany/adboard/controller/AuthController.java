/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adboard.controller;

import com.mycompany.adboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 *
 * @author march
 */
@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверное имя пользователя или пароль");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }
        return "login";
    }
    
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(required = false) String email,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("errorMessage", "Имя пользователя не может быть пустым");
            return "register";
        }
        
        if (password == null || password.length() < 4) {
            model.addAttribute("errorMessage", "Пароль должен содержать минимум 4 символа");
            model.addAttribute("username", username);
            return "register";
        }
        
        String userEmail = (email != null && !email.isEmpty()) ? email : username + "@local.com";
        boolean success = userService.registerUser(username, password, userEmail);
        
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Регистрация прошла успешно");
            return "redirect:/login";
        } else {
            model.addAttribute("errorMessage", "Пользователь с таким именем уже существует");
            model.addAttribute("username", username);
            return "register";
        }
    }
}