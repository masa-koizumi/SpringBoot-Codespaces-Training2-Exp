package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.entity.Asset; // これが重要
import com.example.demo.repository.AssetRepository; // これが重要

@Controller
@RequestMapping("/assets")
public class AssetController {

    private final AssetRepository repo;

    public AssetController(AssetRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("assets", repo.findAll());
        return "assets";
    }

    @PostMapping
    public String create(String name) {
        Asset a = new Asset();
        a.setName(name);
        a.setStatus("AVAILABLE");
        repo.save(a);
        return "redirect:/assets";
    }
}
