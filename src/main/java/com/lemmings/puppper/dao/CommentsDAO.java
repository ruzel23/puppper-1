package com.lemmings.puppper.dao;

import com.lemmings.puppper.model.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsDAO extends PagingAndSortingRepository<Comment, Long> {
    //@Query("select s from Comment s where s.postId = ?1 and s.parent is null")
    List<Comment> findAllByPostId(Long postId, Sort id);

    //List<Comment> findAllByPostIdWhereParentIsNull(Long postId, Sort id);
    //List<Comment> findAllByPostId(Long postId);
}
