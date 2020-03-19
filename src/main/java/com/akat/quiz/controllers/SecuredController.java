package com.akat.quiz.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class SecuredController {

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
}
