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
     * 返却処理（資産IDを受け取って返却する）
     */
    @Transactional
    public void returnAsset(Long assetId) { // 引数名を意味通り assetId に
        // 1. その資産自体が存在するか確認
        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new RuntimeException("資産が存在しません"));

        // 2. その資産に紐づいている「貸出情報」を探す
        // ※ asset_id を元に Loan テーブルから検索するメソッドを Repo に追加する必要があります
        Loan loan = loanRepo.findByAssetId(assetId)
                .orElseThrow(() -> new RuntimeException("この資産は現在貸し出されていません"));

        // 3. 状態を戻して貸出情報を削除
        asset.setStatus("AVAILABLE");
        loanRepo.delete(loan);
        assetRepo.save(asset);
    }
｝
