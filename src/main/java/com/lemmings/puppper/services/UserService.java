package com.lemmings.puppper.services;

import com.lemmings.puppper.dao.RoleDAO;
import com.lemmings.puppper.dao.UserDAO;
import com.lemmings.puppper.model.Role;
import com.lemmings.puppper.model.Status;
import com.lemmings.puppper.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private  UserDAO userDAO;
    @Autowired
    private  RoleDAO roleDAO;
    @Autowired
    private  PasswordEncoder passwordEncoder;


    //регистрация для юзеров
    public User register(User user) {
        Role roleUser = findRoleByName("user");


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleUser);
        user.setStatus(Status.ACTIVE); //default

        User registeredUser = userDAO.save(user);

        return registeredUser;
    }

    public List<User> getAll() {
        List<User> result = userDAO.findAll();
        return  result;
    }

    public User findByEmail(String email) {
        User result = userDAO.findByEmail(email);
        return result;
    }

    public User findById(Long id) {
        return userDAO.findById(id).get();
    }

    public Role findRoleByName(String name) {
        return roleDAO.findByName(name);
    }

    //скорей всего нужна метка и не удалять с базы
    public void delete(Long id) {
        userDAO.deleteById(id);
    }


}
