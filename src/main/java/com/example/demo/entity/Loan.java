package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Loan {

    @Id
    @GeneratedValue
    private Long id;

    private Long assetId;
    private Long userId;

    // getter / setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
