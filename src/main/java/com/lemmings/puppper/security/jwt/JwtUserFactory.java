package com.lemmings.puppper.security.jwt;


import com.lemmings.puppper.model.Role;
import com.lemmings.puppper.model.Status;
import com.lemmings.puppper.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// final или не final
public final class JwtUserFactory {

    public JwtUserFactory() {}

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                //mapToGrantedAuthority(new ArrayList<>(user.getRoles())),
                mapToGrantedAuthority(),
                user.getStatus().equals(Status.ACTIVE)
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(/*List<Role> userRoles*/) {

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(grantedAuthority);
        return list;
    }

}
