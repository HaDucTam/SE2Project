package com.example.se2project.controller;

import com.example.se2project.controller.user.MyUserDetails;
import com.example.se2project.entity.*;
import com.example.se2project.repository.CartProductRepository;
import com.example.se2project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.sql.Date;
import java.time.Period;
import java.util.*;

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
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @GetMapping("/cart")
    public String cartProductList(Model model) {

        User user = getUserFromSession();
        Long ids = user.getUserId();
        List<CartProduct> cartProducts = cartProductService.getCartProduct(ids);
        if(cartProducts.isEmpty()) {
            cartProducts = Collections.emptyList();
            model.addAttribute("cartProducts", cartProducts);
            return "cartPage";
        }
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("total", toTal());

        return "cartPage";
    }
    @GetMapping("/checkout")
    public String checkOut(Model model) {
        User user = getUserFromSession();
        Period dayPlus = Period.ofDays(7);
        LocalDate date = LocalDate.now();
        LocalDate delivery = date.plus(dayPlus);
        Date orderDate = Date.valueOf(date);
        Date deliveryDate = Date.valueOf(delivery);
        if(user.getAddress() == null) {
            model.addAttribute("message", "You need address first");
            return "redirect:/cart";
        }
        Order order = Order.builder().orderDate(orderDate).deliveryDate(deliveryDate).user(getUserFromSession()).deliveryAddress(user.getAddress()).build();
        orderService.insert(order);
        List<CartProduct> cartProductList = cartProductService.getCartProduct(getUserFromSession().getUserId());
        for (CartProduct cartProduct : cartProductList) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order).quantity(cartProduct.getQuantity()).price(cartProduct.total).build();
            Long id = cartProduct.getProduct().getProductId();
            Product product = productService.findById(id).get();
            orderDetail.setProduct(product);
            orderDetailService.insert(orderDetail);
        }
        if(user.getAddress() == null) {
            return "redirect:/cart";
        }
//        Order order = Order.builder().orderDetail(orderDetail).orderDate(orderDate).deliveryDate(deliveryDate).deliveryAddress(user.getAddress()).user(user).build();

        orderService.insert(order);
        model.addAttribute("myOrder", order);
        return "accountPages/orderList";
    }
    public User getUserFromSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
        String username = user1.getUsername();
        User user = userService.getUserByEmail(username);
        return user;
    }
    @GetMapping("/delete/{id}")
    public String deleteCartProduct(@PathVariable Long id) {
        cartProductService.deleteById(id);
        return "redirect:/cart";
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateCart(HttpServletRequest request,
                             HttpSession session, Model model) {
        String[] quantities = request.getParameterValues("quantity");
        List<CartProduct> cartProducts = cartProductService.getCartProduct(getUserFromSession().getUserId());
        for(int i = 0; i < cartProducts.size();i++) {
            cartProducts.get(i).setQuantity(Integer.parseInt(quantities[i]));
        }
        for (CartProduct cartProduct : cartProducts) {
            cartProductService.insert(cartProduct);
        }
        model.addAttribute("cartProducts", cartProducts);
        return "redirect:/cart";
    }

    public double toTal() {
        List<CartProduct> cartProductList = cartProductService.getCartProduct(getUserFromSession().getUserId());
        double total = 0;
        for(CartProduct cartProduct: cartProductList) {
            total += cartProduct.getQuantity() * cartProduct.getProduct().getPrice();
        }
        return total;
    }
}
