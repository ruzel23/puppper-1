package com.lemmings.puppper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    @GetMapping("/comments")
    public ModelAndView getCommentsPage() {
        return new ModelAndView("comments");
    }
    
    @GetMapping
    public ModelAndView getTimelinePage() {
        return new ModelAndView("timeline");
    }
    
}
