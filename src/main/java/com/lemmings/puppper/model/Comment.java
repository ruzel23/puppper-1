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
    @JoinColumn(name = "post")
    @ManyToOne
    private Post post;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "user_id")
    private Long userId;


    public Comment() {}

    public Comment(Long userId, Post post, Long parentId, String content) {
        this.userId = userId;
        this.post = post;
        this.parentId = parentId;
        this.content = content;
    }

    public Comment(Long id, Long userId, Post post, Long parentId, String content) {
        this.id = id;
        this.userId = userId;
        this.post = post;
        this.parentId = parentId;
        this.content = content;
    }
}
