package com.lemmings.puppper.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "id:" + getId() + ", " +
                "name:" + name + "}";
    }

}
