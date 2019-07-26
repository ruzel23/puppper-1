package com.lemmings.puppper.dao;

import com.lemmings.puppper.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
