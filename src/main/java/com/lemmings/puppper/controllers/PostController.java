package com.lemmings.puppper.controllers;

import com.lemmings.puppper.model.Post;
import com.lemmings.puppper.model.User;
import com.lemmings.puppper.services.PostService;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("posts", postService.getAllPosts());

        return "posts";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("pid") Long pid, Model model) {
        Post post = postService.getPostById(pid);
        model.addAttribute("post", post);

        return "fragments/postForm :: postForm";
    }

    @PostMapping
    public String save(Post post, Model model) {
        postService.savePost(post);

        return "redirect:/posts";
    }

    @PutMapping
    public String update(Post post, Model model) {
        postService.updatePost(post);
        model.addAttribute("posts", postService.getAllPosts());

        return "posts";
    }

    @DeleteMapping
    public String remove(@RequestParam Long pid, Model model) {
        postService.deletePost(pid);
        model.addAttribute("post", new Post());
        model.addAttribute("posts", postService.getAllPosts());

        return "posts";
    }
}
