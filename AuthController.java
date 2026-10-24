package com.aipc.planner.controller;

import com.aipc.planner.model.AppUser;
import com.aipc.planner.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    public AuthController(UserRepository users, PasswordEncoder encoder) {
        this.users = users; this.encoder = encoder;
    }

    @GetMapping("/")
    public String index(HttpSession session) {
        return session.getAttribute("userId") == null ? "auth" : "redirect:/dashboard";
    }

    @PostMapping("/register")
    public String register(@RequestParam String phone, @RequestParam String password,
                           @RequestParam String confirmPassword, RedirectAttributes flash) {
        phone = phone.trim();
        if (!phone.matches("[0-9+() -]{8,20}")) {
            flash.addFlashAttribute("error", "Please enter a valid phone number.");
            return "redirect:/";
        }
        if (password.length() < 8) {
            flash.addFlashAttribute("error", "Use a password with at least 8 characters.");
            return "redirect:/";
        }
        if (!password.equals(confirmPassword)) {
            flash.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/";
        }
        if (users.existsByPhone(phone)) {
            flash.addFlashAttribute("error", "An account with this phone number already exists.");
            return "redirect:/";
        }
        users.save(new AppUser(phone, encoder.encode(password)));
        flash.addFlashAttribute("success", "Account created. Please log in.");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String phone, @RequestParam String password,
                        HttpSession session, RedirectAttributes flash) {
        AppUser user = users.findByPhone(phone.trim()).orElse(null);
        if (user == null || !encoder.matches(password, user.getPasswordHash())) {
            flash.addFlashAttribute("error", "Invalid phone number or password.");
            return "redirect:/";
        }
        session.setAttribute("userId", user.getId());
        session.setAttribute("userPhone", user.getPhone());
        return "redirect:/dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
