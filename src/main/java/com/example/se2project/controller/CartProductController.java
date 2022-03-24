package com.example.se2project.controller;

import com.example.se2project.entity.CartProduct;
import com.example.se2project.entity.Product;
import com.example.se2project.entity.User;
import com.example.se2project.repository.CartProductRepository;
import com.example.se2project.service.CartProductService;
import com.example.se2project.service.ProductService;
import com.example.se2project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("userId")
public class CartProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CartProductService cartProductService;

    @Autowired
    CartProductRepository cartProductRepository;
    @Autowired
    UserService userService;

    @GetMapping("/cartProductList")
    public String cartProductList(Model model) {
        List<CartProduct> cartProducts = cartProductService.getCartProduct(1L);
        model.addAttribute("cartProducts", cartProducts);

        return "cart";
    }

    //    @GetMapping
//    public String showCartProduct() {
//
//    }

    @GetMapping("/product/cartProduct/add/{productId}")
//    @SessionAttributes("userId")
    public String addCartProduct(@PathVariable("productId") Long productId,
                                 @SessionAttribute("userId") Long userId

    ) {
        Optional<Product> product = productService.findById(productId);
        User u = userService.findById(userId).get();

        if (product.isPresent()) {
            CartProduct cartProduct = CartProduct.builder()
//                    .name(product.get().getName())
//                    .detail(product.get().getDetail())
//                    .image(product.get().getImagePath())
//                    .price(product.get().getPrice())
//                    .user(u)
                    
                    .build();



            cartProduct.setQuantity(1);
            cartProductRepository.save(cartProduct);

        }
        return "redirect:/cartProductList";
    }




}
