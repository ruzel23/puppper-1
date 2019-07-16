package com.lemmings.puppper.services;

import com.lemmings.puppper.dao.CommentsDAO;
import com.lemmings.puppper.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    private final CommentsDAO commentsDAO;

    public CommentsService(CommentsDAO commentDAO) {
        this.commentsDAO = commentDAO;
    }

    public void createComment(Comment comment) {
        commentsDAO.save(comment);
    }
}
