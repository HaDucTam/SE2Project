package com.example.se2project.repository;

import com.example.se2project.entity.Role;

public interface RoleRepository extends BaseRepository<Role, Long>{
    Role findRoleByName(String name);
}
