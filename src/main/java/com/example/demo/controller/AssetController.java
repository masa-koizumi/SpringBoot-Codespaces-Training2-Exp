package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AssetService;

// ★ これらが必要です
import com.example.demo.entity.Asset;
import com.example.demo.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AssetController {

    @Autowired
    private AssetService service;

    @Autowired
    private AssetRepository assetRepo; // ★ ここが assetRepository になっていませんか？

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

    // AssetController.java

    @GetMapping("/assets/new")
    public String showCreateForm() {
        return "asset-new"; // templates/asset-new.html を指す
    }

    @PostMapping("/assets/new")
    public String createAsset(@RequestParam String name) {
        Asset asset = new Asset();
        asset.setName(name);
        asset.setStatus("AVAILABLE"); // 初期状態は「利用可能」
        assetRepo.save(asset);
        return "redirect:/assets"; // 登録後は一覧へ戻る
    }
    
}
