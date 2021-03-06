package com.example.se2project.controller.user;

import com.example.se2project.entity.Role;
import com.example.se2project.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class MyUserDetails implements UserDetails {
    private User user;

    public MyUserDetails() {
    }

    public MyUserDetails(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

//        for (Role role : roles) {
        authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public boolean hasRole(String rolename) {
        return user.hasRole(rolename);
    }
    public String getFullname() {
        return user.getFullName();
    }
    public void setFirstName(String firstName) {
        this.user.setFirstName(firstName);
    }
    public void setLastName(String lastName) {
        this.user.setLastName(lastName);
    }
    public void setPhoneNumber(String phoneNumber) {
        this.user.setPhoneNumber(phoneNumber);
    }
    public void setAddress(String address) {
        this.user.setAddress(address);
    }
}