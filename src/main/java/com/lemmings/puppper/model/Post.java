package com.lemmings.puppper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "Posts")
public class Post implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    //    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @Column(name = "user_id")
    private Long authorId;
    @Column(name = "content")
    private String content;
    @Column(name = "creation_date")
    private String creationDate;

}