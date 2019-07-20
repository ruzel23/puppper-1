package com.lemmings.puppper.controllers;

import com.lemmings.puppper.model.AjaxBasicReturn;
import com.lemmings.puppper.model.Comment;
import com.lemmings.puppper.model.User;
import com.lemmings.puppper.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
                                         @ModelAttribute("user") User user) {
        Comment comment = new Comment(user, postId, parentId, content);
        try {
            commentsService.createComment(comment);
        }
        catch (Exception e) {
            return new AjaxBasicReturn(false, e.getMessage());
        }

        return new AjaxBasicReturn(true, "");
    }

    @GetMapping("/getComments")
    @ResponseBody
    public Map<Long, Set<Comment>> getComments(@RequestParam("post_id") Long postId) {
        return commentsService.getComments(postId);
    }

    @DeleteMapping("/deleteComment")
    @ResponseBody
    public AjaxBasicReturn deleteComment(@RequestParam("user_id") Long userId,
                                        @RequestParam("comment_id") Long commentId) {
        try {
            commentsService.deleteComment(commentId);
        }
        catch (Exception e) {
            return new AjaxBasicReturn(false, e.getMessage());
        }

        return new AjaxBasicReturn(true, "");
    }
}
