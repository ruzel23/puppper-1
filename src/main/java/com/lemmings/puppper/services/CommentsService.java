package com.lemmings.puppper.services;

import com.lemmings.puppper.dao.CommentsDAO;
import com.lemmings.puppper.dao.PostDAO;
import com.lemmings.puppper.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentsService {
    private final CommentsDAO commentsDAO;
    private final PostDAO postDAO;

    @Autowired
    public CommentsService(CommentsDAO commentDAO, PostDAO postDAO) {
        this.commentsDAO = commentDAO;
        this.postDAO = postDAO;
    }

    public Long createComment(Comment comment) {
        //if (commentsDAO.existsById(comment.getPostId())) {
            Comment freshComment = commentsDAO.save(comment);
            return freshComment.getId();
        //} else {
        //    throw new PostNotExistsException("Пост не существует");
        //}
    }

    public Map<Long, Set<Comment>> getComments(Long postId) {
        List<Comment> comments = commentsDAO.findAllByPostId(postId, new Sort(Sort.Direction.DESC, "id"));
        Map<Long, Set<Comment>> result = new HashMap<>();
        if (!comments.isEmpty()) {
            Comparator<Comment> comparator = Comparator.comparing(Comment::getId);
            result.put(0L, new TreeSet<>(comparator));
            for (Comment c : comments) {
                if (c.getDeleted() != 0) {
                    if (!result.containsKey(c.getId())) {
                        continue;
                    }
                    c.setContent(null);
                }
                if (!result.containsKey(c.getParent())) {
                    result.put(c.getParent(), new TreeSet<>(comparator));
                }
                result.get(c.getParent()).add(c);
            }
        }
        return result;
    }

    public void deleteComment(Long commentId, Long userId) {
        commentsDAO.deleteComment(commentId, userId);
    }

    public void editComment(Long commentId, String content, Long userId) {
        commentsDAO.editComment(commentId, content, userId);
    }
}
