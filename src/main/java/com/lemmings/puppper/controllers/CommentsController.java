package com.lemmings.puppper.controllers;

import com.lemmings.puppper.model.AjaxBasicReturn;
import com.lemmings.puppper.model.Comment;
import com.lemmings.puppper.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
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
        String userName = cookie[1].getValue();
        Long userId = Long.parseLong(cookie[2].getValue());
        Long freshCommentId;
        try {
            Comment comment = new Comment(userId, userName, postId, parentId, content);
            freshCommentId = commentsService.createComment(comment);
        }
        catch (Exception e) {
            return new AjaxBasicReturn(false, e.getMessage());
        }

        return new AjaxBasicReturn(true, freshCommentId.toString());
    }

    @PostMapping("/editComment")
    @ResponseBody
    public AjaxBasicReturn editComment(@RequestParam("id") Long id,
                                         @RequestParam("content") String content) {
        try {
            commentsService.editComment(id, content);
        }
        catch (Exception e) {
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
    public AjaxBasicReturn deleteComment(@RequestParam("comment_id") Long commentId) {
        try {
            commentsService.deleteComment(commentId);
        }
        catch (Exception e) {
            return new AjaxBasicReturn(false, e.getMessage());
        }

        return new AjaxBasicReturn(true, commentId.toString());
    }
}
