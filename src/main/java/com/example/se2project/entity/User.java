package com.example.se2project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@Entity
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(length = 30, nullable = false, name = "first_name")
    private String firstName;

    @Column(length = 30, nullable = false, name = "last_name")
    private String lastName;

    @Column(length = 30, nullable = false)
    private String phoneNumber;

    @Column(length = 50, nullable = false)
    private String address;

    private int active;

    @Column(nullable = false)
    private String role;

    public User(long userId,String email, String password,String firstName, String lastName, String phoneNumber, String address, String role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.active = 1;
        this.role = role;
    }
    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }

        return new ArrayList<String>();
    }
}
