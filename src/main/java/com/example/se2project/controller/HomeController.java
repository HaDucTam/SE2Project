package com.example.se2project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/products","/"})
public class HomeController {

    @GetMapping
    public String viewProducts(){
        System.out.println("vao viewProduct() method");
        return "view-productDetail";
    }


    @GetMapping("/{id}")
    public String gg(){
        System.out.println("vao gg() method");
        return "showProduct";
    }
}
