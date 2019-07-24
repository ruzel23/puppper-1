package com.lemmings.puppper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(name = "content")
    @Size(min=1, max=160)
    private String content;
    @NotNull
    @Column(name = "post_id")
    private Long postId;
    @NotNull
    @Column(name = "parent_id")
    private Long parent;
    @NotNull
    @Column(name = "user_id")
    private Long userId;
    @NotBlank
    @Column(name = "user_name")
    private String userName;
    @DecimalMax("1")
    @DecimalMin("0")
    @Column(name = "deleted")
    private Integer deleted;

    public Comment() {}

    public Comment(Long userId, String userName, Long postId, Long parent, String content) {
        this.userId = userId;
        this.userName = userName;
        this.postId = postId;
        this.parent = parent;
        this.content = content;
        this.deleted = 0;
    }
}
