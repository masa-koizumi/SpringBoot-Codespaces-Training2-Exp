package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Asset {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String status; // AVAILABLE / BORROWED

    // getter / setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
