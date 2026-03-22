package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 追加（推奨）

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import java.time.LocalDate; // ★ 追加：日付を扱うために必要

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepo;

    @Autowired
    private AssetRepository assetRepo;

    @Autowired
    private UserRepository userRepo;

    /**
     * 貸出処理（引数を追加）
     */
    @Transactional // ★ 貸出情報の保存と資産の状態更新をセットで行うため追加
    public void loan(Long assetId, Long userId, LocalDate loanDate, Integer periodDays) {
        
        // 1. 資産の取得とチェック
        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new RuntimeException("資産が存在しません"));

        if ("LOANED".equals(asset.getStatus())) {
            throw new RuntimeException("貸出中のため貸出不可");
        }

        // 2. ユーザの取得
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("ユーザが存在しません"));

        // 3. Loanオブジェクトの作成とセット
        Loan loan = new Loan();
        loan.setAsset(asset);
        loan.setUser(user);
        loan.setLoanDate(loanDate);    // ★ 追加：貸出日をセット
        loan.setPeriodDays(periodDays); // ★ 追加：期間をセット

        // 4. 資産のステータス更新
        asset.setStatus("LOANED");

        // 5. 保存
        loanRepo.save(loan);
        assetRepo.save(asset);
    }

    /**
     * 返却処理（資産IDをベースに実行）
     */
    @Transactional
    public void returnAsset(Long assetId) {
        // 1. 資産IDに紐づく「進行中の貸出」を検索
        Loan loan = loanRepo.findByAssetId(assetId)
                .orElseThrow(() -> new RuntimeException("この資産の貸出情報が見つかりません (AssetID: " + assetId + ")"));

        // 2. 資産の状態を更新
        Asset asset = loan.getAsset();
        asset.setStatus("AVAILABLE");

        // 3. 貸出レコードを削除し、資産状態を保存
        loanRepo.delete(loan);
        assetRepo.save(asset);
    }

｝
