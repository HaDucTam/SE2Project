package com.example.se2project.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/admin/users"})
public class UserManager {

    @GetMapping
    public String viewPage(){
        return "adminPages/users/userList";
    }
}
