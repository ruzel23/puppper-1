package com.lemmings.puppper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemmings.puppper.model.Comment;
import com.lemmings.puppper.model.Post;
import com.lemmings.puppper.model.User;
import com.lemmings.puppper.services.CommentsService;
import com.lemmings.puppper.services.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {
	
	private final List<Post> posts = new ArrayList<>();
	private final List<Comment> comments = new ArrayList<>();
	private final User user = new User();
	private Post post;
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PostService postService;
	
	@MockBean
	private CommentsService commentsService;
	
	@Before
	public void initPosts() {
		user.setId(1L);
		user.setEmail("test");
		user.setName("test");
		user.setPassword("test");
		
		post = new Post(1L, "test", LocalDateTime.now(), user, comments);
		Comment comment = new Comment();
		
		post.getComments().add(comment);
		posts.add(post);
	}
	
	@Test
	public void testListPosts() throws Exception {
		when(postService.getAllPosts()).thenReturn(posts);
		
		mockMvc.perform(get("/posts").accept(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetPost() throws Exception {
		when(postService.getPostById(any())).thenReturn(Optional.of(post));
		
		mockMvc.perform(get("/posts/1").accept(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", is(1)))
		       .andExpect(jsonPath("$.content", is("test")));
		
	}
	
	@Test
	public void testCreatePost() throws Exception {
		when(postService.save(any())).thenReturn(post);
		String content = objectMapper.writeValueAsString(post);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/posts")
		                                      .contentType(MediaType.APPLICATION_JSON_VALUE)
		                                      .content(content)
		                                      .accept(MediaType.APPLICATION_JSON_VALUE))
		       .andExpect(status().isCreated())
		       .andExpect(MockMvcResultMatchers.content().json(content))
		       .andReturn();
	}
	
}