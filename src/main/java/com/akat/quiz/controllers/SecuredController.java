package com.akat.quiz.controllers;

import com.akat.quiz.model.security.User;
import org.springframework.web.bind.annotation.*;

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
