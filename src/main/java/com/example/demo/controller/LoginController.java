package com.example.demo.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import com.example.demo.entity.User;
import com.example.demo.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    
    @Autowired
    private LoginService service;

    @GetMapping("/")
    public String index() {
        return "login";
    }

@PostMapping("/login")
public String login(
    @RequestParam String username,
    @RequestParam String password,
    HttpSession session,
    RedirectAttributes ra) {

    if (loginService.authenticate(username, password)) {
        session.setAttribute("user", username);
        return "redirect:/menu";
    } else {
        ra.addFlashAttribute("error", "ログイン失敗");
        return "redirect:/login";
    }
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
