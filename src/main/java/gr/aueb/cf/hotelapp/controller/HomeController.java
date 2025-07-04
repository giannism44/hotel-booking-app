package gr.aueb.cf.hotelapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "pages/home";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/about";
    }

    @GetMapping("/management")
    public String managementPage() {
        return "pages/management";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "pages/register";
    }

    @GetMapping("/login")
    public String login(Principal principal) {
        return (principal == null) ? "pages/loginb" : "redirect:/dashboard";
    }

    @GetMapping("/spa")
    public String spa() {
        return "pages/spa";
    }

    @GetMapping("/restaurant")
    public String restaurant() {
        return "pages/restaurant";
    }
}