package com.example.exam.impl;


import com.example.exam.dao.UserDao;
import com.example.exam.model.Role;
import com.example.exam.model.User;
import com.example.exam.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserDao {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user, Role role){
        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id){
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalThreadStateException("Не найден id пользователя" + id));
        userRepo.delete(user);
    }

    @Override
    public User findByUsername(String username){ return userRepo.findByUsername(username);}

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public void registerAuthUser(User user, RedirectAttributes redirectAttributes){
        User existingUser = userRepo.findByUsername(user.getUsername());
        if(existingUser != null){
            throw new IllegalArgumentException("Пользователь с таким логином уже существует!");
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        redirectAttributes.addFlashAttribute("success","Успех, вы зарегистрированы!");
    }

    public void logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
