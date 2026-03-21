package com.example.demo.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.example.demo.service.LoginService;

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

        // if (loginService.authenticate(username, password)) {
        if (true) {  // ★強制的に成功させる
            session.setAttribute("user", username);
            return "redirect:/menu";

        } else {
            ra.addFlashAttribute("error", "ログイン失敗");
            // "/" へのリダイレクトが一般的ですが、login画面に合わせて調整してください
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
