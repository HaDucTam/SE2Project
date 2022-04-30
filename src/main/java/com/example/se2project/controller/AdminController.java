package com.example.se2project.controller;

import com.example.se2project.entity.Category;
import com.example.se2project.entity.Order;
import com.example.se2project.entity.Product;
import com.example.se2project.entity.User;
import com.example.se2project.entity.dto.ProductDto;
import com.example.se2project.service.CategoryService;
import com.example.se2project.service.OrderService;
import com.example.se2project.service.ProductService;
import com.example.se2project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping({"/admin"})
public class AdminController {

    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderService orderService;
    @GetMapping
    public String viewAdminPage(){
        return "adminPages/adminDashboard";
    }

    @GetMapping("/categories")
    public String showCategoryList(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryLists", categoryList);
        return "adminPages/categories/categoryList";
    }

    @GetMapping("/products")
    public String showProductList(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productLists", productList);
        return "adminPages/products/productList";
    }

    @GetMapping("/orders")
    public String showOrderList(Model model) {
        List<Order> orderList = orderService.findAll();
        model.addAttribute("orderLists", orderList);
        return "adminPages/orders/orderList";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userLists", userList);
        return "adminPages/users/userList";
    }

    @GetMapping("/categories/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category",new Category());
        return "/adminPages/categories/categoryAdd";
    }

    @GetMapping("/products/add")
    public String addProductForm(Model model) {
        model.addAttribute("products",new ProductDto());
        model.addAttribute("categories",categoryService.findAll());
        return "adminPages/products/productAdd";
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("products",new ProductDto());
        model.addAttribute("categories",categoryService.findAll());
        return "adminPages/users/userAdd";
    }

    @PostMapping("/categories/add/save")
    public String saveCategory(@RequestParam(value = "name", required = false, defaultValue = "empty name") String name,
                               @RequestParam(value = "description", required = false, defaultValue = "empty description") String description,
                               @RequestParam(value = "categoryImage", required = false) MultipartFile categoryImage) throws IOException {
        String fileName = StringUtils.cleanPath(categoryImage.getOriginalFilename());
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setImage(fileName);
        Category savedCategory = categoryService.add(category);
        String uploadDir1 = "category-image/" + savedCategory.getCategoriesId();
        Path uploadPath1 = Paths.get(uploadDir1);
        if(!Files.exists(uploadPath1)) {
            Files.createDirectories(uploadPath1);
        }
        try {
            InputStream inputStream = categoryImage.getInputStream();
            Path filePath = uploadPath1.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            throw new IOException("could not save uploaded file: " + fileName);
        }

        return "redirect:/";
    }

    @PostMapping("/products/add/save")
    public String saveProduct(@ModelAttribute("products") ProductDto productDto,
                              @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Product product = new Product();
        product.setProductId(productDto.getId());
        product.setName(productDto.getName());
        product.setDetail(productDto.getDetail());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findById(productDto.getCategoryId()).get());
        product.setImage(fileName);
        Product savedProduct = productService.add(product);
        String uploadDir = "product-image/" + savedProduct.getProductId();
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try {
            InputStream inputStream = image.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            throw new IOException("could not save uploaded file: " + fileName);
        }
        return "redirect:/";

    }





}
