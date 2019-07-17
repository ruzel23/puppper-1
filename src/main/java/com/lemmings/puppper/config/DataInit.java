package com.lemmings.puppper.config;

import com.lemmings.puppper.dao.CommentsDAO;
import com.lemmings.puppper.dao.PostDAO;
import com.lemmings.puppper.dao.UserDAO;
import com.lemmings.puppper.model.Comment;
import com.lemmings.puppper.model.Post;
import com.lemmings.puppper.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {

    private UserDAO userDAO;
    private PostDAO postDAO;
    private CommentsDAO commentsDAO;


    @Autowired
    public DataInit(UserDAO userDAO, PostDAO postDAO, CommentsDAO commentsDAO) {
        this.userDAO = userDAO;
        this.postDAO = postDAO;
        this.commentsDAO = commentsDAO;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = commentsDAO.count();
        User user = new User(1L,
                "Rinatio",
                "mrsalatikus@gmail.com",
                "qwerty");
        userDAO.save(user);
        Post post = new Post(1L, "my first post", user);
        postDAO.save(post);
        if (count == 0) {
            Comment c1 = new Comment(1L, 1L, post, 0L, "My first comment");
            //Comment c2 = new Comment(2L, 1L, post, null, "My second comment");

            commentsDAO.save(c1);
            //commentsDAO.save(c2);
        }

    }

    
}