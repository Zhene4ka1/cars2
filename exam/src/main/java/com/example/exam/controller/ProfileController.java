package com.example.exam.controller;


import com.example.exam.impl.UserImpl;
import com.example.exam.model.Role;
import com.example.exam.model.User;
import com.example.exam.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserImpl userImpl;
    private final UserRepo userRepo;


    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login?logout";
        }
        String username = principal.getName();
        User user = userImpl.findByUsername(username);

        if (user == null) {
            return "redirect:/login?logout";
        }

        model.addAttribute("isAdmin", user.getRoles().contains(Role.ADMIN));
        model.addAttribute("user", user);
        return "profile";
    }

}































