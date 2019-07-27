package com.lemmings.puppper.security.jwt;

import com.lemmings.puppper.exceptions.NotFoundCookieException;
import com.lemmings.puppper.model.User;
import com.lemmings.puppper.services.UserService;
import com.lemmings.puppper.util.CookieManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtTokenFilter extends OncePerRequestFilter {


    private UserService userService;

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {

            Authentication auth = jwtTokenProvider.getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            validateUserCookies(request);

        }
        filterChain.doFilter(request, response);
    }

    private void validateUserCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        try {
            String tokenCookie = CookieManager.getToken(cookies);
            String userNameCookie = CookieManager.getUserName(cookies);
            Long userIdCookie = CookieManager.getUserId(cookies);
            String userRoleCookie = CookieManager.getRoleName(cookies);

            String email = jwtTokenProvider.getEmail(tokenCookie);
            User user = userService.findByEmail(email);

            if (!userIdCookie.equals(user.getId())
                    | !userNameCookie.equals(user.getName())
                    | !userRoleCookie.equals(user.getRole().getName())) {
                throw new JwtAuthenticationException("Cookie invalid");
            }
        } catch (NullPointerException | NotFoundCookieException e) {
            throw new JwtAuthenticationException("Cookie invalid");
        }
    }
}
