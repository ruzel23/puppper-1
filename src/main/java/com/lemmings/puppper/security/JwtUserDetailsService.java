package com.lemmings.puppper.security;


import com.lemmings.puppper.model.User;
import com.lemmings.puppper.security.jwt.JwtUser;
import com.lemmings.puppper.security.jwt.JwtUserFactory;
import com.lemmings.puppper.services.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email: " + email + "not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }

}
