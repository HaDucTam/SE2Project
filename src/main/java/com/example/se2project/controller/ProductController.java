package com.example.se2project.controller;
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

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/product/addProduct")
    public String addProductForm(Model model) {
        model.addAttribute("products",new ProductDto());
        model.addAttribute("categories",categoryService.findAll());
        return "productAdd";
    }
    @GetMapping("/product/category/{id}")
    public String productByCategory(@PathVariable Long id, Model model) {
        model.addAttribute("allCategory", categoryService.findAll());
//        model.addAttribute("cartCount")?
        model.addAttribute("products", productService.getProductByCategoryId(id));
        return "showProduct";
    }
    @GetMapping("/product/addCategory")
    public String addCategoryForm(Model model) {
        model.addAttribute("category",new Category());
        return "categoryAdd";
    }
    @PostMapping("/addCategory/save")
    public String saveCategory(@RequestParam(value = "name", required = false, defaultValue = "empty name") String name,
                               @RequestParam(value = "description", required = false, defaultValue = "empty description") String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        categoryService.add(category);
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
    @GetMapping("/product/productList")
    public String viewProduct(Model model) {
        List<Product> products = productService.findAll();
        if(!products.isEmpty()){
            model.addAttribute("products", products);
            model.addAttribute("allCategory", categoryService.findAll());
        }
        return "showProduct";
    }
    @GetMapping(value = "/product/{id}")
    public String getProductById(@PathVariable(value = "id") Long id, Model model) {
//        Employee employee = employeeRepository.getById(id);
//        model.addAttribute("employee", employee);
        return "employeeDetail";
    }

}
