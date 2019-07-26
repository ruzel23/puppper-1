package com.lemmings.puppper.controllers;

import com.lemmings.puppper.model.Role;
import com.lemmings.puppper.model.Server;
import com.lemmings.puppper.model.User;
import com.lemmings.puppper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("server", new Server());
        return "admin";
    }

    @PostMapping("/admin")
    public ModelAndView admin(@ModelAttribute("server") Server server,
                              HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        User admin = userService.makeAdmin(currentUserEmail, server.getPassword());
        Cookie roleNameCookie = new Cookie("role", admin.getRole().getName());
        response.addCookie(roleNameCookie);
        return new ModelAndView("welcome");
    }
}
