package com.example.se2project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 30, nullable = false, name = "first_name")
    private String firstName;

    @Column(length = 30, nullable = false, name = "last_name")
    private String lastName;

    @Column(length = 30, nullable = true)
    private String phoneNumber;

    @Column(length = 50, nullable = true)
    private String address;

    @Column(length = Integer.MAX_VALUE, nullable = true)
    private String image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Role role;
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public boolean hasRole(String roleName) {

        if (role.getName().equals(roleName)) {
            return true;
        }

        return false;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Transient
    public String getImagePath() {
        if(image == null || userId == 0) {
            return null;
        }
        return "/user-image/" + userId + "/" + image;
    }
}
