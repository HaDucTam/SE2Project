package com.example.se2project.service;

import com.example.se2project.entity.Role;

public interface RoleService extends BaseService<Role, Long>{
    Role findRoleByName(String name);
}
