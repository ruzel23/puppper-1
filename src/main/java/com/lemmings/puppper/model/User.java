package com.lemmings.puppper.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    //
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public User() {

    }

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}