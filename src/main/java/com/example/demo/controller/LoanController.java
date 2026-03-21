package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Loan;
import com.example.demo.repository.*;
import com.example.demo.service.LoanService;

import java.util.List;

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

    /**
     * 貸出管理画面
     */
    @GetMapping("/loans")
    public String list(Model model) {

        // 貸出一覧
        List<Loan> loans = loanRepo.findAll();

        // 貸出可能な資産のみ表示（改善ポイント）
        model.addAttribute("assets", assetRepo.findByStatus("AVAILABLE"));

        // ユーザ一覧
        model.addAttribute("users", userRepo.findAll());

        model.addAttribute("loans", loans);

        return "loans";
    }

    /**
     * 貸出処理
     */
    @PostMapping("/loans")
    public String loan(@RequestParam Long assetId,
                       @RequestParam Long userId,
                       RedirectAttributes ra) {

        try {
            loanService.loan(assetId, userId);

            // 成功メッセージ
            ra.addFlashAttribute("message", "貸出が完了しました");

        } catch (Exception e) {

            // エラーメッセージ
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/loans";
    }

    /**
     * 返却処理
     */
    @PostMapping("/loans/return")
    public String returnAsset(@RequestParam Long loanId,
                             RedirectAttributes ra) {

        try {
            loanService.returnAsset(loanId);
            ra.addFlashAttribute("message", "返却しました");

        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/loans";
    }
}
