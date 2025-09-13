package dev.kalbarczyk.sportsmanager.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class AuthController {
    @GetMapping("/login")
    public String loginPage() {
        return "modules/auth/login";
    }
}
