package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password; 

    // id の getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // name の getter/setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // ★ password の getter/setter を追加
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // User.java

    @Column(name = "role")
    private String role; // "ADMIN" または "USER" が入る想定

    // Getter と Setter
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
