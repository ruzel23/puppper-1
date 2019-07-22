package com.lemmings.puppper.dao;

import com.lemmings.puppper.model.Comment;
import com.lemmings.puppper.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDAO extends PagingAndSortingRepository<User, Long> {
}
