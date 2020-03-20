package com.akat.quiz.configs;

import com.akat.quiz.model.security.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**How to start using Spring Security
     * 1) Add dependency
     * 2) Create Enum com.akat.quiz.model.security.Role
     * 3) @EnableWebSecurity SecurityConfig   that extends WebSecurityConfigurerAdapter - step by step
     *
     *
     *
     *
     * */

    @Autowired
    DataSource dataSource;

    //Step 1 - show how to take users
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource);
    }

    //Step 2 - configure encoding
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    //Step 3 - configure http requests by roles

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/secured/admin/**").hasAnyRole(RoleType.ADMIN.getThisAndHigherPriorities())
                .antMatchers("/secured/premium-user/**").hasAnyRole(RoleType.PREMIUM_USER.getThisAndHigherPriorities())
                .antMatchers("/secured/user/**").hasAnyRole(RoleType.USER.getThisAndHigherPriorities())
                .antMatchers("/quiz/**").permitAll() //anybody can use controller "quiz"
                .and()
                .formLogin();
//                .loginPage("/login").failureUrl("/login-error");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/secured/registration/**");
    }
}
