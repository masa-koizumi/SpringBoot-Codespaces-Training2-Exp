package com.example.demo.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.example.demo.service.LoginService;
import com.example.demo.entity.User;

@Controller
public class LoginController {

    private final LoginService loginService;

    // コンストラクタ注入（@Autowiredは不要です）
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
        @RequestParam String username,
        @RequestParam String password,
        HttpSession session,
        RedirectAttributes ra) {

        // 1. 認証チェック
        if (loginService.authenticate(username, password)) {
            
            // 2. 認証成功後、DBからユーザー情報（roleを含む）を取得
            User user = loginService.login(username); 
            
            // 3. セッションに "user" という名前で Userオブジェクトを保存
            // これにより、session.user.role で権限が取れるようになります
            session.setAttribute("user", user);
            
            return "redirect:/menu";

        } else {
            ra.addFlashAttribute("error", "ログイン失敗");
            return "redirect:/"; 
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) { // ← ra を追加！
        // セッション破棄
        session.invalidate();

        ra.addFlashAttribute("message", "ログアウトしました");
        
        // ログイン画面（ルート）へ
        return "redirect:/";
    }
}
