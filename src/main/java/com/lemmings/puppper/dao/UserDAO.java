package com.lemmings.puppper.dao;

import com.lemmings.puppper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {


    User findByEmail(String email);


}
