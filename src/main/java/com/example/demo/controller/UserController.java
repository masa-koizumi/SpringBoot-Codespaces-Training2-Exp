package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @PostMapping("/users")
    public String create(@RequestParam String name) {
        User u = new User();
        u.setName(name);
        userRepo.save(u);
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String delete(@RequestParam Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}
