package com.example.se2project.service.impl;

import com.example.se2project.entity.Role;
import com.example.se2project.repository.RoleRepository;
import com.example.se2project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long, RoleRepository> implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    public Role findRoleByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        return role;
    }
}
