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
     * 貸出入力画面を表示する
     */
    @GetMapping("/loans/new")
    public String showLoanForm(@RequestParam Long assetId, Model model) {
        // 1. URLパラメータの assetId から資産情報を取得
        com.example.demo.entity.Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new RuntimeException("資産が見つかりません"));

        // 2. 画面に渡すデータをセット
        model.addAttribute("asset", asset);
        // ユーザー一覧（プルダウン用）
        model.addAttribute("users", userRepo.findAll());
        
        // 3. 表示するHTMLの名前（loan-new.html を想定）
        return "loan-new"; 
    }
    /**
     * 貸出処理
     */
    @PostMapping("/loans")
    public String loan(@RequestParam Long assetId,
                       @RequestParam Long userId,
                       @RequestParam String loanDate, // ★追加: HTMLからはStringで届く
                       @RequestParam Integer periodDays, // ★追加
                       RedirectAttributes ra) {

        try {
            // Service側に新しい引数を渡せるように後でServiceも微調整が必要です
            loanService.loan(assetId, userId, java.time.LocalDate.parse(loanDate), periodDays);

            ra.addFlashAttribute("message", "貸出が完了しました");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/assets";
    }
    /**
     * 返却処理
     */
    @PostMapping("/loans/return")
    public String returnAsset(@RequestParam Long loanId, RedirectAttributes ra) {
        try {
            // もしService側が「資産ID」で返却する作りならこのままでOK
            loanService.returnAsset(loanId); 
            ra.addFlashAttribute("message", "返却しました");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/assets"; // 資産一覧に戻る
    }
    
}
