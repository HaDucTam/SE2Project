package com.example.se2project.controller;
import com.example.se2project.controller.user.MyUserDetails;
import com.example.se2project.entity.CartProduct;
import com.example.se2project.entity.Category;
import com.example.se2project.entity.Product;
import com.example.se2project.entity.User;
import com.example.se2project.entity.dto.ProductDto;
import com.example.se2project.service.CategoryService;
import com.example.se2project.service.ProductService;
import com.example.se2project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("userId")
@RequestMapping({"/product"})
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;



    //    @GetMapping("/shop/category/{id}")
//    public String productByCategory(@PathVariable Long id, Model model) {
////        model.addAttribute("cartCount")?
//
////        model.addAttribute("allCategory", categoryService.findAll());
//        model.addAttribute("products", productService.getProductByCategoryId(id));
//        return "shopPage";
//    }
//    @GetMapping("/shop")
//    public String viewShop(Model model) {
//        List<Product> products = productService.findAll();
////        if(!products.isEmpty()){
//            model.addAttribute("products", products);
//            model.addAttribute("allCategory", categoryService.findAll());
////        }
//        return "shopPage";
//    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id,
                            HttpServletRequest servletRequest, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
        String username = user1.getUsername();
        User user = userService.getUserByEmail(username);
//        User u = (User) model.getAttribute("userId");
//        Long ids = (Long) servletRequest.getSession().getAttribute("userId");
//        System.out.println(getUser(ids));
        Long ids = user.getUserId();
        if(session.getAttribute("cart") == null) {
            List<CartProduct> cartProductList = new ArrayList<CartProduct>();
            cartProductList.add(new CartProduct(productService.findById(id).get(), getUser(ids), 1));
            System.out.println(cartProductList);
            session.setAttribute("cart", cartProductList);
//
        }else {
            List<CartProduct> cartProducts = (List<CartProduct>) session.getAttribute("cart");
            int index = isExisted(id, cartProducts);
            if(index == -1) {
                cartProducts.add(new CartProduct(productService.findById(id).get(), getUser(ids),1));
            }else{
                int quantity = cartProducts.get(index).getQuantity() + 1;
                cartProducts.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cartProducts);
        }
        return "redirect:/cart";
    }
    //    @GetMapping("/")
//    public String viewProduct(Model model) {
//        List<Product> products = productService.findAll();
//        if(!products.isEmpty()){
//            model.addAttribute("products", products);
//            model.addAttribute("allCategory", categoryService.findAll());
//        }
//        return "homePage";
//    }
//    @GetMapping(value = "/product/{id}")
//    public String getProductById(@PathVariable(value = "id") Long id, Model model) {
////        Employee employee = employeeRepository.getById(id);
////        model.addAttribute("employee", employee);
//        return "employeeDetail";
//    }
    public User getUser(@SessionAttribute("userId") Long userId) {
        return userService.findById(userId).get();
    }
    public int isExisted(Long id, List<CartProduct> cartProducts) {
        for(int i = 0; i < cartProducts.size();i++) {
            if(cartProducts.get(i).getProduct().getProductId() == id) {
                return i;
            }
        }
        return -1;
    }
    @GetMapping(value = "/{id}")
    public String getProductById(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.findById(id).get();
        model.addAttribute("productById", product);
        return "productDetail";
    }


}
