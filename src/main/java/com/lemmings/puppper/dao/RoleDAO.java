package com.lemmings.puppper.dao;

import com.lemmings.puppper.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
