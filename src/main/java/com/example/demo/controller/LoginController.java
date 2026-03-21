package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import com.example.demo.entity.User;
import com.example.demo.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService service;

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, HttpSession session) {

        User user = service.login(name);
        session.setAttribute("user", user);

        return "redirect:/menu";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        // セッション破棄
        session.invalidate();

        ra.addFlashAttribute("message", "ログアウトしました");
        
        // ログイン画面へ
        return "redirect:/";
    }
    
}
