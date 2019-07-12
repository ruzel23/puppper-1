package com.lemmings.puppper.dao;

import com.lemmings.puppper.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CommentsDAO extends JpaRepository<Comment, Long> {
}
