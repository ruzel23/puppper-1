package com.lemmings.puppper.services;

import com.lemmings.puppper.dao.PostRepository;
import com.lemmings.puppper.model.Post;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Post> getUserPosts(Long authorId) {
        return postRepository.findAllByAuthorId(authorId);
    }

    @Transactional(readOnly = true)
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

//    public Post update(Post post) {
//        Post toUpdate = postRepository.getOne(post.getId());
//        toUpdate.setContent(post.getContent());
//
//        return postRepository.save(toUpdate);
//    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }


}
