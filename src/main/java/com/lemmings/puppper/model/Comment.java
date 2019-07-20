package com.lemmings.puppper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

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
    private Long parent;
    //@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    //@JsonInclude()
    //@Transient
    //private List<Comment> children = new LinkedList<>();
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name")
    private String userName;

    public Comment(User user, Long postId, String content) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.postId = postId;
        this.content = content;
    }

    public Comment() {}

    public Comment(User user, Long postId, Long parent, String content) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.postId = postId;
        this.parent = parent;
        this.content = content;
    }
}
