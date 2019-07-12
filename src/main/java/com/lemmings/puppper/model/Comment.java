package com.lemmings.puppper.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "post_id")
    private Long postId;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "user_id")
    private Long userId;

    public Comment(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }

    public Comment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
