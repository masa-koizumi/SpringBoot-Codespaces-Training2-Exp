package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Optional;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

@Service
public class LoanService {

    private final AssetRepository assetRepo;
    private final UserRepository userRepo;
    private final LoanRepository loanRepo;

    public LoanService(AssetRepository assetRepo,
                       UserRepository userRepo,
                       LoanRepository loanRepo) {
        this.assetRepo = assetRepo;
        this.userRepo = userRepo;
        this.loanRepo = loanRepo;
    }

    public void borrow(Long assetId, Long userId) {

        // Asset取得
        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        // User取得
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 状態チェック
        if (!"AVAILABLE".equals(asset.getStatus())) {
            throw new RuntimeException("Asset already borrowed");
        }

        // Loan作成
        Loan loan = new Loan();
        loan.setAssetId(assetId);
        loan.setUserId(userId);
        loanRepo.save(loan);

        // 状態変更
        asset.setStatus("BORROWED");
        assetRepo.save(asset);
    }
}
