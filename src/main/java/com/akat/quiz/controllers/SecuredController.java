package com.akat.quiz.controllers;

import com.akat.quiz.model.security.User;
import com.akat.quiz.model.security.UserDto;
import com.akat.quiz.services.interfaces.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secured")
public class SecuredController {

    @Autowired
    private ISecurityService securedService;


    @GetMapping("/admin")
    public String enteredAsAdmin() {
        return "Admin";
    }

    @GetMapping("/premium-user")
    public String enteredAsPremium() {
        return "Premium user";
    }

    @GetMapping("/user")
    public String enteredAsUser() {
        return "User";
    }

    @PostMapping("/registration")
    public User registerUser(@RequestBody UserDto dto) {
        return securedService.registerNewUserAccount(dto);
    }
}
