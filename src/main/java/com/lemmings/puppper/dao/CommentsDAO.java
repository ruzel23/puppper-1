package com.lemmings.puppper.dao;

import com.lemmings.puppper.model.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentsDAO extends PagingAndSortingRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postId, Sort id);

    @Modifying
    @Transactional
    @Query("update Comment c set c.deleted = 1 where c.id = ?1 and c.userId = ?2")
    void deleteComment(Long commentId, Long userId);

    @Modifying
    @Transactional
    @Query("update Comment c set c.content = ?2 where c.id = ?1 and c.userId = ?3")
    void editComment(Long id, String content, Long userId);
}
