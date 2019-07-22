package com.lemmings.puppper.services;

import com.lemmings.puppper.dao.PostRepository;
import com.lemmings.puppper.model.Post;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

//    public List<Post> getUserPosts(Long authorId) {
//        return postRepository.findAllByAuthorId(authorId);
//    }

    public Post getPostById(Long id) {
        return postRepository
                .findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public void savePost(Post post) {
        post.setCreationDate(LocalDateTime.now().toString());
        postRepository.save(post);
    }

    public Post updatePost(Post post) {
        Post toUpdate = postRepository.getOne(post.getId());
        toUpdate.setContent(post.getContent());

        return postRepository.saveAndFlush(toUpdate);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


}
