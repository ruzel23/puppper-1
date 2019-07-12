package com.lemmings.puppper.controllers;

import com.lemmings.puppper.model.AjaxBasicReturn;
import com.lemmings.puppper.model.Comment;
import com.lemmings.puppper.model.User;
import com.lemmings.puppper.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CommentsController {

    private CommentsService commentsService;

    @Autowired
    public void setCommentsService(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/createComment")
    @ResponseBody
    public AjaxBasicReturn createComment(@RequestParam("content") String content,
                                         @RequestParam("post_id") Long postId,
                                         @RequestParam("user_id") Long userId) {
        Comment comment = new Comment(userId, postId, content);
        try {
            commentsService.createComment(comment);
        }
        catch (Exception e) {
            e.printStackTrace();

            return new AjaxBasicReturn(false, e.getMessage());
        }

        return new AjaxBasicReturn(true, "");
    }
}
