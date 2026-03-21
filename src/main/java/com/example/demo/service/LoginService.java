
package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.*;
import com.example.demo.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    @Autowired
    private UserRepository userRepo;

    public User login(String name) {

        return userRepo.findByName(name)
                .orElseGet(() -> {
                    User u = new User();
                    u.setName(name);
                    return userRepo.save(u);
                });
    }
}
