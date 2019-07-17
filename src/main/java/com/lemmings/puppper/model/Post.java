package com.lemmings.puppper.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "posts")
public class Post implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "content")
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public Post(){}

    public Post(Long id, String content, User user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

}
