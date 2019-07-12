package com.lemmings.puppper.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String name;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
}
