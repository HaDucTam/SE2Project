package com.example.se2project.controller;

import com.example.se2project.controller.user.MyUserDetails;
import com.example.se2project.entity.CartProduct;
import com.example.se2project.entity.Order;
import com.example.se2project.entity.User;
import com.example.se2project.repository.CartProductRepository;
import com.example.se2project.service.CartProductService;
import com.example.se2project.service.OrderService;
import com.example.se2project.service.ProductService;
import com.example.se2project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.sql.Date;
import java.time.Period;
import java.util.List;

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

    @GetMapping("/cart")
    public String cartProductList(Model model, HttpServletRequest servletRequest) {

        User user = getUserFromSession();
        Long ids = user.getUserId();
        List<CartProduct> cartProducts = cartProductService.getCartProduct(ids);
        model.addAttribute("cartProducts", cartProducts);

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
            return "redirect:/cart";
        }
        Order order = Order.builder().orderDate(orderDate).orderDate(orderDate).deliveryDate(deliveryDate).deliveryAddress(user.getAddress()).user(user).build();
        orderService.insert(order);
        model.addAttribute("my-order", order);
        return "accountPages/orderList";
    }
    public User getUserFromSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
        String username = user1.getUsername();
        User user = userService.getUserByEmail(username);
        return user;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateCart(HttpServletRequest request,
                             HttpSession session) {
        String[] quantities = request.getParameterValues("quantity");
        List<CartProduct> cartProducts = (List<CartProduct>) session.getAttribute("cart");
        for(int i = 0; i < cartProducts.size();i++) {
            cartProducts.get(i).setQuantity(Integer.parseInt(quantities[i]));
        }
        session.setAttribute("cartProducts", cartProducts);

        return "redirect:/cart";
    }

    private double toTal(HttpSession session) {
        List<CartProduct> cartProductList = (List<CartProduct>) session.getAttribute("cartProducts");
        double total = 0;
        for(CartProduct cartProduct: cartProductList) {
            total += cartProduct.getQuantity() * cartProduct.getProduct().getPrice();
        }
        return total;
    }
    @GetMapping()
    public String cart(ModelMap modelMap, HttpSession session) {
        modelMap.put("total", toTal(session));
        return "cartPage";
    }

//    @GetMapping("/product/cartProduct/add/{productId}")
////    @SessionAttributes("userId")
//    public String addCartProduct(@PathVariable("productId") Long productId,
//                                 @SessionAttribute("userId") Long userId
//
//    ) {
//        Optional<Product> product = productService.findById(productId);
//        User u = userService.findById(userId).get();
//
//        if (product.isPresent()) {
//            CartProduct cartProduct = CartProduct.builder()
////                    .name(product.get().getName())
////                    .detail(product.get().getDetail())
////                    .image(product.get().getImagePath())
////                    .price(product.get().getPrice())
////                    .user(u)
//
//                    .build();
//
//
//
//            cartProduct.setQuantity(1);
//            cartProductRepository.save(cartProduct);
//
//        }
//        return "redirect:/cartProductList";
//    }




}
