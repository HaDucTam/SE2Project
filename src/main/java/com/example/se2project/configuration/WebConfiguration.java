package com.example.se2project.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/product/**", "/addProduct/**", "/addCategory/**").hasAnyAuthority("Admin")
//                .antMatchers("/users/**").hasAnyAuthority("User")
                .antMatchers("/product/addToCart/**").hasAnyAuthority("User")
                .antMatchers("/admin/**").hasAnyAuthority("Admin")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
//                .successHandler(logInterceptor)
                .permitAll()
                .and().logout().permitAll()
        ;
    }
//    @Override
//    public void configure(WebSecurity webSecurity) throws Exception {
//        we
//    }

}
