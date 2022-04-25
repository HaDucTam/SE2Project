package com.example.se2project.controller;

import com.example.se2project.entity.Product;
import com.example.se2project.entity.User;
import com.example.se2project.service.ProductService;
import com.example.se2project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/"})
public class HomeController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @GetMapping
    public String listProducts(Model model){
        System.out.println("vao viewProduct() method");

        List<Product> newArrival = productService.getProductsByProductIdBetween(Long.valueOf(productService.findAll().size() - 5), Long.valueOf(productService.findAll().size()));
        model.addAttribute("newArrival", newArrival);
        return "homePage";
    }
}
