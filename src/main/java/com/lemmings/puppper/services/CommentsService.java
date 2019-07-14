package com.lemmings.puppper.services;

import com.lemmings.puppper.dao.CommentsDAO;
import com.lemmings.puppper.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    private final CommentsDAO commentsDAO;

    public CommentsService(CommentsDAO commentDAO) {
        this.commentsDAO = commentDAO;
    }

    public void createComment(Comment comment) {
        commentsDAO.save(comment);
    }

    public List<Comment> getComments(Long postId) {
        return commentsDAO.findAllByPostId(postId);
    }

    public void deleteComment(Long commentId) {
        commentsDAO.deleteById(commentId);
    }

    public void editComment(Comment comment) {
        commentsDAO.save(comment);
    }
}
