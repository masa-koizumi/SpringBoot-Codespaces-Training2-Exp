package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepo;

    // ★ 追加：Controllerから呼ばれているメソッド
    public boolean authenticate(String name, String password) {
        // 現在のロジックではパスワードチェックがないため、
        // ユーザーが存在すればOKとするか、パスワード比較ロジックをここに追加します。
        // return userRepo.findByName(name).isPresent();
        return true; // 全員
    }

    // 既存のメソッド（必要であれば残しておく）
    public User login(String name) {
        return userRepo.findByName(name)
                .orElseGet(() -> {
                    User u = new User();
                    u.setName(name);
                    return userRepo.save(u);
                });
    }
}
