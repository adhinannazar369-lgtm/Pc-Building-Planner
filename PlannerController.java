package com.aipc.planner.controller;

import com.aipc.planner.model.AppUser;
import com.aipc.planner.model.SavedBuild;
import com.aipc.planner.repository.SavedBuildRepository;
import com.aipc.planner.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;

@Controller
public class PlannerController {
    private final UserRepository users;
    private final SavedBuildRepository builds;
    public PlannerController(UserRepository users, SavedBuildRepository builds) {
        this.users = users; this.builds = builds;
    }

    private AppUser currentUser(HttpSession session) {
        Object id = session.getAttribute("userId");
        if (id == null) return null;
        return users.findById((Long) id).orElse(null);
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        AppUser user = currentUser(session);
        if (user == null) return "redirect:/";
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("savedBuilds", builds.findByUserIdOrderByCreatedAtDesc(user.getId()));
        return "dashboard";
    }

    @PostMapping("/save-build")
    public String saveBuild(@RequestParam String purpose, @RequestParam int budget,
                            @RequestParam String componentsJson, HttpSession session,
                            RedirectAttributes flash) {
        AppUser user = currentUser(session);
        if (user == null) return "redirect:/";
        if (budget < 30000 || budget > 200000) {
            flash.addFlashAttribute("error", "Budget must be between ₹30,000 and ₹2,00,000.");
            return "redirect:/dashboard";
        }
        if (componentsJson.length() > 3900) {
            flash.addFlashAttribute("error", "Build data is too large to save.");
            return "redirect:/dashboard";
        }
        builds.save(new SavedBuild(user, purpose, budget, componentsJson));
        flash.addFlashAttribute("success", "Build saved to MySQL successfully.");
        return "redirect:/dashboard";
    }
}
