package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.repository.*;
import com.example.demo.service.LoanService;

@Controller
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepo;

    @Autowired
    private AssetRepository assetRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/loans")
    public String list(Model model) {

        model.addAttribute("loans", loanRepo.findAll());
        model.addAttribute("assets", assetRepo.findAll());
        model.addAttribute("users", userRepo.findAll());

        return "loans";
    }

    @PostMapping("/loans")
    public String loan(@RequestParam Long assetId,
                       @RequestParam Long userId,
                       RedirectAttributes ra) {

        try {
            loanService.loan(assetId, userId);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/loans";
    }

    @PostMapping("/loans/return")
    public String returnAsset(@RequestParam Long loanId) {

        loanService.returnAsset(loanId);
        return "redirect:/loans";
    }
}
