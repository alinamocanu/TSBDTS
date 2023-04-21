package com.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@Profile("mysql")
public class SecurityJdbcConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register", "/login", "/login-error", "/", "/decorations", "h2-console", "/access_denied").permitAll()
                .antMatchers("/decorations/new", "customers/**", "decorations/delete", "decorations/update").hasRole("ADMIN")
                .antMatchers("/cart/**", "/orders/**", "/bankAccounts/**").hasAnyRole("CUSTOMER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authUser")
                .failureUrl("/login-error")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");
    }
}