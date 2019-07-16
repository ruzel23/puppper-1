package com.lemmings.puppper.controllers;

import com.lemmings.puppper.model.Post;
import com.lemmings.puppper.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/timeline")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String showAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);

        return "timeline";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);

        return id.toString();
    }

    @PostMapping
    public String createPost(@RequestParam("authorId") Long authorId,
                             @RequestParam("text") String text,
                             Model model) {
        postService.createNewPost(authorId, text);

        return "redirect:/timeline";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String text,
                         Model model) {
        postService.updatePost(id, text);

        return "redirect:/" + id;
    }
}
