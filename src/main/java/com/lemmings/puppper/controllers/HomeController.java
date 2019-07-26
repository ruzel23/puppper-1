package com.lemmings.puppper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/commTest")
public class HomeController {

    @GetMapping
    public ModelAndView getCommentsPage() {
        return new ModelAndView("comments");
    }
    
    @GetMapping("/timeline")
    public String getPostsPage(Model model) {
        return "posts";
    }
}
