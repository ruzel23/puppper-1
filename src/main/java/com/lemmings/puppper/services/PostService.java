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

    public List<Post> getAllPostsByUserId(Long userId) {
        return postRepository.findAll(Example.of(
                Post.builder()
                        .authorId(userId)
                        .build()));
    }

    public Post getPostById(Long id) {
        return findPost(id).orElse(new Post());
    }

    public Post createNewPost(Post post) {
        return postRepository.save(post);

//
//                Post.builder()
//                        .authorId(userId)
//                        .content(content)
//                        .creationDate(LocalDateTime.now().toString())
//                        .build());
    }

    public Post updatePost(Long id, String content) {
        Optional<Post> toUpdate = findPost(id);

        if (toUpdate.isPresent()) {
            Post updated = toUpdate.get();
            updated.setContent(content);

            return postRepository.save(updated);
        }

        return new Post();
    }

    public void deletePost(Long id) {
        findPost(id).ifPresent(postRepository::delete);
    }

    private Optional<Post> findPost(Long id) {
        return postRepository.findById(id);
    }

}
