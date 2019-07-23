package com.lemmings.puppper.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Transient
    private List<Role> roles;

    public User() {
    }

    public User(Long id, String name, String email, String password, Status status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
        roles = new ArrayList<>();
        roles.add(role);
    }

    public void setRole(Role role) {
        this.role = role;
        System.out.println("Role: " + role.toString());
        roles = new ArrayList<>();
        roles.add(role);
    }
}
