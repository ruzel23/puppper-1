package com.lemmings.puppper.config;

import com.lemmings.puppper.security.jwt.JwtAuthenticationEntryPoint;
import com.lemmings.puppper.security.jwt.JwtConfigurer;
import com.lemmings.puppper.security.jwt.JwtTokenProvider;
import com.lemmings.puppper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/signup").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().logout().deleteCookies("access_token", "user_name", "user_id", "role").logoutUrl("/logout")
                    .logoutSuccessUrl("/login").clearAuthentication(true)
                .and().exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider, userService));
    }
}
