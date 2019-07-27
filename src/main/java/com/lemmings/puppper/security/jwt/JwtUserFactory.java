package com.lemmings.puppper.security.jwt;



import com.lemmings.puppper.model.Status;
import com.lemmings.puppper.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;


public final class JwtUserFactory {

    public JwtUserFactory() {}

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthority(user),
                user.getStatus().equals(Status.ACTIVE)
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(User user) {

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().getName());
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(grantedAuthority);
        return list;
    }

}
