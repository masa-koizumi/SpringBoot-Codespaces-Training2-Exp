package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

import com.example.demo.service.LoanService;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @PostMapping
    public void borrow(@RequestBody Map<String, Long> req) {
        Long assetId = req.get("assetId");
        Long userId = req.get("userId");

        service.borrow(assetId, userId);
    }
}
