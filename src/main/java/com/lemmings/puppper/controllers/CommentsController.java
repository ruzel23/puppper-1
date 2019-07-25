package com.lemmings.puppper.controllers;

import com.lemmings.puppper.model.AjaxBasicReturn;
import com.lemmings.puppper.model.Comment;
import com.lemmings.puppper.services.CommentsService;
import com.lemmings.puppper.util.CookieManager;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    private CommentsService commentsService;

    @Autowired
    public void setCommentsService(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/addComment")
    @ResponseBody
    public AjaxBasicReturn createComment(@RequestParam("content") String content,
                                         @RequestParam("post_id") Long postId,
                                         @RequestParam("parent_id") Long parentId,
                                         HttpServletRequest request) {
        Cookie[] cookie = request.getCookies();
        try {
            String userName = CookieManager.getUserName(cookie);
            Long userId = CookieManager.getUserId(cookie);
            Comment comment = new Comment(userId, userName, postId, parentId, content);
            Long freshCommentId = commentsService.createComment(comment);
            return new AjaxBasicReturn(true, freshCommentId.toString());
        } catch (DataIntegrityViolationException e) {
            return new AjaxBasicReturn(false, "Пост не существует!");
        } catch (Exception e) {
            return new AjaxBasicReturn(false, "Неизвестная ошибка.");
        }
    }

    @PostMapping("/editComment")
    @ResponseBody
    public AjaxBasicReturn editComment(@RequestParam("id") Long commentId,
                                       @RequestParam("content") String content,
                                       HttpServletRequest request) {
        Cookie[] cookie = request.getCookies();
        try {
            Long userId = CookieManager.getUserId(cookie);
            commentsService.editComment(commentId, content, userId);
        } catch (Exception e) {
            return new AjaxBasicReturn(false, e.getMessage());
        }

        return new AjaxBasicReturn(true, content);
    }

    @GetMapping("/getComments")
    @ResponseBody
    public Map<Long, Set<Comment>> getComments(@RequestParam("post_id") Long postId) {
        return commentsService.getComments(postId);
    }

    @DeleteMapping("/deleteComment")
    @ResponseBody
    public AjaxBasicReturn deleteComment(@RequestParam("comment_id") Long commentId, HttpServletRequest request) {
        Cookie[] cookie = request.getCookies();
        try {
            Long userId = CookieManager.getUserId(cookie);
            commentsService.deleteComment(commentId, userId);
        } catch (Exception e) {
            return new AjaxBasicReturn(false, e.getMessage());
        }

        return new AjaxBasicReturn(true, commentId.toString());
    }
}
