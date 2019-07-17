package com.lemmings.puppper.services;

import com.lemmings.puppper.dao.CommentsDAO;
import com.lemmings.puppper.model.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentsService {
    private final CommentsDAO commentsDAO;

    public CommentsService(CommentsDAO commentDAO) {
        this.commentsDAO = commentDAO;
    }

    public void createComment(Comment comment) {
        commentsDAO.save(comment);
    }

    public Map<Comment, List<Comment>> getComments(Long postId) {
        List<Comment> comments = commentsDAO.findAllByPostId(postId ,new Sort(Sort.Direction.ASC, "id"));
        Map<Comment, List<Comment>> result = new TreeMap<>(Comparator.comparing(Comment::getId));
        for (Comment c : comments) {
            if (c.getParentId() != null) {
                result.get(c.getParentId()).add(c);
            } else {
                result.put(c, new ArrayList<>());
            }
        }
        return result;
    }

    public void deleteComment(Long commentId) throws Exception {
        if (commentsDAO.existsById(commentId)) {
            commentsDAO.deleteById(commentId);
        } else {
            throw new Exception("Комментарий не существует");
        }
    }
}
