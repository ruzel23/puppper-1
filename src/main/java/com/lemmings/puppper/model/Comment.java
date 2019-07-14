package com.lemmings.puppper.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
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

    public Comment(Long userId, Long postId, Long parentId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.parentId = parentId;
        this.content = content;
    }
}
