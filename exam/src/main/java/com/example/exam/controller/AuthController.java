package com.example.exam.controller;

import com.example.exam.impl.UserImpl;
import com.example.exam.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserImpl userImpl;

    @GetMapping("/registration")
    public String regist(Model model){
        model.addAttribute("user",new User());
        return "registration";
    }

    @PostMapping("/registration/save")
    public String registration(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes){
        model.addAttribute("user",user);
        userImpl.registerAuthUser(user, redirectAttributes);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String logout(){return "auth";}

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        userImpl.logout(request, response);
        return "redirect:/login?logout";
    }
}
