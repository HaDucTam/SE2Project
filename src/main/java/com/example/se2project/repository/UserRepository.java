package com.example.se2project.repository;

import com.example.se2project.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    User findUserByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    User getUserByEmail(String email);
}
