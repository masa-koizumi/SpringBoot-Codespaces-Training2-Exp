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



    // ... 既存の list, create, delete メソッド ...

    /**
     * ★ 追加：パスワード設定画面を表示
     */
// パスワード暗号化（本来はBCrypt等を使いますが、まずは簡易実装で進めます）
@GetMapping("/users/setup-password")
public String showPasswordForm(@RequestParam Long userId, Model model) {
    com.example.demo.entity.User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
    model.addAttribute("user", user);
    return "user/setup-password"; // templates/user/setup-password.html を指す
}

@PostMapping("/users/setup-password")
public String setupPassword(@RequestParam Long userId, @RequestParam String password, RedirectAttributes ra) {
    com.example.demo.entity.User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
    
    // パスワードをセット（本来は暗号化を推奨）
    user.setPassword(password);
    userRepo.save(user);
    
    ra.addFlashAttribute("message", user.getName() + "さんのパスワードを更新しました");
    return "redirect:/users";
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
