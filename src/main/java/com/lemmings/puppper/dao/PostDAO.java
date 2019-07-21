package com.lemmings.puppper.dao;

import com.lemmings.puppper.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDAO extends PagingAndSortingRepository<Post, Long> {
}
