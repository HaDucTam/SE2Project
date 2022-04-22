package com.example.se2project.controller;

import com.example.se2project.entity.Product;
import com.example.se2project.service.CategoryService;
import com.example.se2project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"/shop"})
public class ShopController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @GetMapping
    public String listProducts(Model model){
        List<Product> productList = productService.getProductsByProductIdBetween(Long.valueOf(productService.findAll().size() - 2), Long.valueOf(productService.findAll().size()));
        if(!productList.isEmpty()) {
            model.addAttribute("products", productList);
            model.addAttribute("allCategory", categoryService.findAll());
        }
        System.out.println("vao viewProduct() method");
        return "shopPage";
    }
    @GetMapping("/category/{id}")
    public String productByCategory(@PathVariable Long id, Model model) {
//        model.addAttribute("cartCount")?

        model.addAttribute("allCategory", categoryService.findAll());
        model.addAttribute("products", productService.getProductByCategoryId(id));
        return "shopPage";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable String id){
        System.out.println("vao gg() method");
        return "productDetail";
    }
}
