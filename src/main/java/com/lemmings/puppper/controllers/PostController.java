package com.lemmings.puppper.controllers;

import com.lemmings.puppper.dao.CommentsDAO;
import com.lemmings.puppper.dao.UserDAO;
import com.lemmings.puppper.exceptions.ResourceNotFoundException;
import com.lemmings.puppper.model.Comment;
import com.lemmings.puppper.model.Post;
import com.lemmings.puppper.model.User;
import com.lemmings.puppper.services.CommentsService;
import com.lemmings.puppper.services.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	private final CommentsService commentsService;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Post> listPosts() {
		return postService.getAllPosts();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{pid}")
	public Post getPost(@PathVariable Long pid) {
		return findPost(pid);
	}
	
	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody @Valid Post post,
	                                       BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("invalid request data");
		}
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(postService.save(post));
	}
	
	@PutMapping("/{pid}")
	public ResponseEntity<Post> updatePost(@RequestBody Post post,
	                                       @PathVariable Long pid,
	                                       BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("invalid request data");
		}
		
		Post toUpdate = findPost(pid);
		toUpdate.setContent(post.getContent());
		
		return ResponseEntity.ok(postService.save(toUpdate));
	}
	
	@DeleteMapping("/{pid}")
	public void deletePost(@PathVariable Long pid) {
		Post post = findPost(pid);
		postService.delete(pid);
	}
	
	@PostMapping("/{pid}/comments")
	public void createComment(@PathVariable Long pid,
	                          Comment comment) {
		Post post = findPost(pid);
		post.getComments().add(comment);
	}
	
	@GetMapping("/{pid}/comments/{cid}")
	public Comment getComment(@PathVariable Long pid,
	                          @PathVariable Long cid) {
		return findPost(pid).getComments()
		                    .stream()
		                    .filter(comment -> comment.getId().equals(cid))
		                    .findFirst()
		                    .orElseThrow(ResourceNotFoundException::new);
	}
	
	private Post findPost(Long pid) {
		return postService.getPostById(pid).orElseThrow(ResourceNotFoundException::new);
	}
}
