package com.lemmings.puppper.security.jwt;

import com.lemmings.puppper.model.User;
import com.lemmings.puppper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


//подробней разобраться
public class JwtTokenFilter extends GenericFilterBean {


    private UserService userService;

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateToken(token)) {


            Authentication auth = jwtTokenProvider.getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            validateUserCookies((HttpServletRequest) request);

        }
        filterChain.doFilter(request, response);
    }

    private void validateUserCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String tokenCookie = cookies[0].getValue();
        String userNameCookei = cookies[1].getValue();
        Long userIdCookie = Long.valueOf(cookies[2].getValue());

        String email = jwtTokenProvider.getEmail(tokenCookie);
        User user = userService.findByEmail(email);
        System.out.println("I WAS HERE ==================================================================================");
        if (userIdCookie != user.getId() && userNameCookei != user.getName()) {
            throw new JwtAuthenticationException("Cookie invalid");
        }
    }
}
