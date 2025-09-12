package dev.kalbarczyk.sportsmanager.auth.controller;

import dev.kalbarczyk.sportsmanager.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/register")
    public String registerPage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password) {
        userService.register(username, password, false);
        return "redirect:/login";
    }
}
