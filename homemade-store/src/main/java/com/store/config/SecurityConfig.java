package com.store.config;

import com.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@Profile("mysql")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
                .and()
                .withUser("guest")
                .password(passwordEncoder().encode("1234"))
                .roles("GUEST");

    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                    .dataSource(dataSource)
//                    .usersByUsernameQuery("select username,password,enabled "
//                            + "from customers "
//                            + "where username = ?")
//                    .authoritiesByUsernameQuery("select username, roleName "
//                        + "from customers c "
//                        + "join roles_customers rc on (rc.customerId = c.customerId) "
//                        + "join roles r on (r.roleId = rc.roleId) "
//                        + "where username = ?");
//                    auth.userDetailsService(userDetailsService);
//        }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied")
                .and()
                .authorizeRequests()
                .mvcMatchers("/register", "/login", "/login-error", "/", "/decorations", "h2-console").permitAll()
                .mvcMatchers("/decorations/new").hasRole("ADMIN")
                .mvcMatchers("/cart/**", "/orders/**", "/bankAccounts/**").hasAnyRole("CUSTOMER", "ADMIN")
                .and()
                .csrf();
    }

    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> response.sendRedirect("/");
    }

    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/login-error");
        };
    }

}
