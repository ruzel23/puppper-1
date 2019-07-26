package com.lemmings.puppper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
@Table(name = "Posts")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 140)
    private String content;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "postId",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
  
}