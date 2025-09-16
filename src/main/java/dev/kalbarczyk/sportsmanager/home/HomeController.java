package dev.kalbarczyk.sportsmanager.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling requests to the home page.
 */
@RequestMapping("/")
@Controller
public class HomeController {
    /**
     * Displays the home page.
     *
     * @return the view name for the home page
     */
    @GetMapping
    public String index() {
        return "modules/home/index";
    }
}
