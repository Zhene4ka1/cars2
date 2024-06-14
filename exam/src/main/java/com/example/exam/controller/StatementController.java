package com.example.exam.controller;


import com.example.exam.impl.StatementImpl;
import com.example.exam.impl.UserImpl;
import com.example.exam.model.Statement;
import com.example.exam.model.Status;
import com.example.exam.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;



@Controller
@RequiredArgsConstructor
@RequestMapping("/statements")
public class StatementController {
    private final StatementImpl statementImpl;
    private final UserImpl userImpl;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllStatements(Model model, Principal principal){
        String username = principal.getName();
        List<Statement> statements= statementImpl.getStatementUser(username);
        Collections.reverse(statements);
        model.addAttribute("statement", statements);
        User user = userImpl.findByUsername(username);
        model.addAttribute("user", user);
        return "admin";
    }

    @PostMapping("/updateStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateStatus(@PathVariable Long id, @RequestParam String status){
        statementImpl.updateStatus(id, Status.valueOf(status));
        return "redirect:/statements/";
    }



    @GetMapping("/user")
    public String getUserStatement(Model model, Principal principal){
        if(principal != null){
            String username = principal.getName();
            List<Statement> statements = statementImpl.getStatementUser(username);
            Collections.reverse(statements);
            model.addAttribute("statement", statements);
            User user = userImpl.findByUsername(username);
            model.addAttribute("user",user);
            return "userStatement";
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/statements/create")
    public String getStatements(Model model, Principal principal){
        String username = principal.getName();
        List<Statement> userStatement = statementImpl.getStatementUser(username);
        model.addAttribute("userStatement", userStatement);
        User user = userImpl.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "userAddStatements";
    }

    @PostMapping("/add")
    public String addStatement(@ModelAttribute Statement statement, Principal principal, Model model){
        if(principal!=null){
            String username = principal.getName();
            statementImpl.save(statement, username);
            User user = userImpl.findByUsername(username);
            model.addAttribute("user", user);

            return "redirect:/statements/user";
        }
        return "redirect:/login?logout";
    }
}
