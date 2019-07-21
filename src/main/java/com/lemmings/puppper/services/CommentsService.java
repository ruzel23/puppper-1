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

    public Long createComment(Comment comment) {
        Comment freshComment = commentsDAO.save(comment);
        return freshComment.getId();
    }

    public Map<Long, Set<Comment>> getComments(Long postId) {
        List<Comment> comments = commentsDAO.findAllByPostId(postId ,new Sort(Sort.Direction.ASC, "id"));
        Comparator<Comment> comparator = Comparator.comparing(Comment::getId);
        Map<Long, Set<Comment>> result = new HashMap<>();
        result.put(0L, new TreeSet<>(comparator));
        for (Comment c : comments) {
            if (c.getParent() != null) {
                if (!result.containsKey(c.getParent())) {
                    result.put(c.getParent(), new TreeSet<>(comparator));
                }
                result.get(c.getParent()).add(c);
            } else {
                result.get(0L).add(c);
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

    public void editComment(Long id, String content) {
        //ToDo добавить проверку на пользователя и на существование коммента.
        commentsDAO.editComment(id, content);
    }
}
