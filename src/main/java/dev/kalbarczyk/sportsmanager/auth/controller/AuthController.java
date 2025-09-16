package dev.kalbarczyk.sportsmanager.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for handling authentication-related views.
 */
@AllArgsConstructor
@Controller
public final class AuthController {
    /**
     * Handles HTTP GET requests for the login page.
     *
     * @return the name of the Thymeleaf template for the login page
     */
    @GetMapping("/login")
    public String loginPage() {
        return "modules/auth/login";
    }
}
