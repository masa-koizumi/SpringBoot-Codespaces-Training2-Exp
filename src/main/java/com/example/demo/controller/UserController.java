package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.LoanRepository;

// --- 追加する import ---
import com.example.demo.service.LoginService;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LoanRepository loanRepo;



    // ★ 追加：LoginServiceを注入する
    @Autowired
    private LoginService loginService;
    
    /**
     * ユーザ一覧
     */
    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    /**
     * ユーザ登録
     */
    @PostMapping("/users")
    public String create(@RequestParam String name,
                         RedirectAttributes ra) {

        if (name == null || name.isBlank()) {
            ra.addFlashAttribute("error", "ユーザ名を入力してください");
            return "redirect:/users";
        }

        User u = new User();
        u.setName(name);
        u.setRole("User"); // ★ デフォルトロールを設定

        userRepo.save(u);

        ra.addFlashAttribute("message", "ユーザを登録しました");

        return "redirect:/users";
    }

    /**
     * ユーザ削除（貸出中チェックあり）
     */
    @PostMapping("/users/delete")
    public String delete(@RequestParam Long id,
                         RedirectAttributes ra) {

        // 貸出中チェック（重要）
        if (!loanRepo.findByUserId(id).isEmpty()) {
            ra.addFlashAttribute("error", "貸出中のユーザは削除できません");
            return "redirect:/users";
        }

        userRepo.deleteById(id);

        ra.addFlashAttribute("message", "ユーザを削除しました");

        return "redirect:/users";
    }



    /**
     * パスワード設定画面を表示
     */
    @GetMapping("/user/setup-password")
    public String showSetupPasswordForm(@RequestParam(required = false) Long userId, Model model) {
        model.addAttribute("users", userRepo.findAll());
        if (userId != null) {
            User targetUser = userRepo.findById(userId).orElse(null);
            model.addAttribute("targetUser", targetUser);
        }
        return "user/setup-password";
    }

    /**
     * パスワードを設定
     */
    @PostMapping("/user/setup-password")
    public String setupPassword(@RequestParam("userId") Long userId,
                                @RequestParam("password") String password,
                                RedirectAttributes ra) {
        
        User user = userRepo.findById(userId).orElse(null);

        if (user == null) {
            ra.addFlashAttribute("error", "指定されたユーザが見つかりません");
            return "redirect:/user/setup-password";
        }

        user.setPassword(password); // 本来は暗号化
        userRepo.save(user);

        ra.addFlashAttribute("message", user.getName() + "さんのパスワードを設定しました。");

        return "redirect:/user/setup-password";
    }

    /**
     * ★ 追加：ロールをAdminに変更
     */
    @PostMapping("/users/update-role")
    public String updateRole(@RequestParam Long id, RedirectAttributes ra) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        
        user.setRole("Admin");
        userRepo.save(user);
        
        ra.addFlashAttribute("message", user.getName() + "さんの権限をAdminに変更しました");
        return "redirect:/users";
    }
}
