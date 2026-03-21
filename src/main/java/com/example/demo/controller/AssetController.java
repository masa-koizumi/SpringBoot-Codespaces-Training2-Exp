package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AssetService;

@Controller
public class AssetController {

    @Autowired
    private AssetService service;

    @GetMapping("/assets")
    public String list(Model model) {
        model.addAttribute("assets", service.findAll());
        return "assets";
    }

    @PostMapping("/assets")
    public String create(@RequestParam String name) {
        service.save(name);
        return "redirect:/assets";
    }
}
