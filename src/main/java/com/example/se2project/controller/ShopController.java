package com.example.se2project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/shop"})
public class ShopController {

    @GetMapping
    public String listProducts(){
        System.out.println("vao viewProduct() method");
        return "shopPage";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable String id){
        System.out.println("vao gg() method");
        return "productDetail";
    }
}
