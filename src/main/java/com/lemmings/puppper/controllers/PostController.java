package com.lemmings.puppper.controllers;

import com.lemmings.puppper.model.Post;
import com.lemmings.puppper.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/timeline")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String list(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("post", new Post());
        model.addAttribute("posts", posts);

        return "timeline";
    }

    @GetMapping("/{pid}")
    public String show(@RequestParam(name = "pid", required = true) String pid, Model model) {
        Post post = postService.getPostById(Long.parseLong(pid));
        model.addAttribute("post", post);

        return pid;
    }

    @PostMapping
    public String add(@RequestParam("authorId") String authorId,
                      Post post,
                      Model model) {
        post.setAuthorId(Long.parseLong(authorId));
        post.setCreationDate(LocalDateTime.now().toString());
        postService.createNewPost(post);
        model.addAttribute("posts", postService.getAllPosts());

        return "timeline";
    }

    @PutMapping("/{pid}")
    public String edit(@PathVariable String pid,
                       @RequestParam String text,
                       Model model) {
        postService.updatePost(Long.parseLong(pid), text);

        return "redirect:/" + pid;
    }

    @PostMapping("/{pid}")
    public String remove(@PathVariable String pid) {
        postService.deletePost(Long.parseLong(pid));

        return "redirect:/timeline";
    }
}
