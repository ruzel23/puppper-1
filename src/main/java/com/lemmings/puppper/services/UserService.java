package com.lemmings.puppper.services;

import com.lemmings.puppper.dao.UserDAO;
import com.lemmings.puppper.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findById(Long id) {
        return userDAO.findAllById(id).get(0);
    }

}
