package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepo;

    @Autowired
    private LoanRepository loanRepo;

    public List<Asset> findAll() {
        // 1. リポジトリから全件取得
        // 2. そのまま返す（名前のセットは不要になったため）
        return assetRepo.findAll();
    }

    public void save(String name) {
        Asset a = new Asset();
        a.setName(name);
        a.setStatus("AVAILABLE");
        assetRepo.save(a);
    }
}
