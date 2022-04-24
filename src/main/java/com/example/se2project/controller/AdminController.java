package com.example.se2project.controller;//package com.onlineshopping.se2finalproject.Controller;

import com.example.se2project.entity.Category;
import com.example.se2project.entity.Product;
import com.example.se2project.entity.dto.ProductDto;
import com.example.se2project.service.CategoryService;
import com.example.se2project.service.ProductService;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/admin"})
public class AdminController {

    @Autowired
    ProductService productService;
    @Autowired
    private CategoryService categoryService;

//    @RequestMapping("/admin/users")
//    public String showUserList(Model model) {
//        List<User> userList = userService.listAllUser();
//        model.addAttribute("userList", userList);
//        return "userList";
//    }

    @GetMapping("/addProduct")
    public String addProductForm(Model model) {
        model.addAttribute("products",new ProductDto());
        model.addAttribute("categories",categoryService.findAll());
        return "adminPages/products/productAdd";
    }
    @GetMapping("/addCategory")
    public String addCategoryForm(Model model) {
        model.addAttribute("category",new Category());
        return "/adminPages/categories/categoryAdd";
    }
    @PostMapping("/addCategory/save")
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
    @PostMapping("/addProduct/save")
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


//    @RequestMapping("/register")
//    public String showRegisterForm(Model model) {
//        model.addAttribute("user", new User());
////        model.addAttribute("pageTitle", "REGISTER");
//        return "registerUser";
//    }

//    @RequestMapping("/saveUser")
//    public String saveUser(@RequestParam(value = "id", required = false) Long id,
//                           User user) {
//        user.setId(id);
//        userRepository.save(user);
//        return "redirect:/";
//    }





}
