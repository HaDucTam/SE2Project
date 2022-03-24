//package com.example.se2project.controller;
//
//import com.example.se2project.entity.Cart;
//import com.example.se2project.entity.Product;
//import com.example.se2project.service.CartProductService;
//import com.example.se2project.service.ProductService;
//import com.example.se2project.util.Utils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//@SessionAttributes("userId")
//public class CartController {
//    @Autowired
//    ProductService productService;
//    @Autowired
//    CartProductService cartProductService;
//
////    public static List<Product> cart = new ArrayList<>();
////    @GetMapping("/cart")
////    public String getCartList(Model model) {
////        model.addAttribute("cartCount", cart.size());
////        model.addAttribute("totalPrice", cart.stream().mapToDouble(Product::getPrice).sum());
////        model.addAttribute("cart", cart);
////        return "cart";
////    }
////    @GetMapping("/addToCart/{id}")
////    public String addToCart(@PathVariable("id") Long id) {
////        cart.add(productService.findById(id).get());
////        return "redirect:/cart";
////    }
////    @GetMapping("/addToCart/{id}")
////    public String addToCart(@PathVariable("id") Long id, @RequestParam("quantity") int quantity, HttpSession session, Model model) {
////        Optional<Product> product = productService.findById(id);
//////        CartProduct cartProduct = CartProduct.builder().product(product.get()).quantity(quantity).price(product.get().getPrice()).build();
//////        cartProductService.insert(cartProduct);
////        return "redirect:/cart";
////    }
////    @GetMapping("/cart/deleteCart/{id}")
////    public String deleteCartItem(@PathVariable Long id) {
////        cart.remove(id);
////        return "redirect:/cart";
////    }
////    public int isExisting(Long id, HttpSession session) {
////        List<Product> cartItem = (List<Product>) session.getAttribute("cart");
////        for(int i = 0; i < cartItem.size();i++) {
////            if(cartItem.get(i).getProductId() == id) {
////                return i;
////            }
////        }
////        return -1;
////    }
//
//    @GetMapping("/addToCart/{id}")
//    public String addToCart(HttpServletRequest request, Model model,
//                            @RequestParam(value = "id", defaultValue = "") Long id) {
//        Product product = null;
//        if(id != null) {
//            product = productService.findById(id).get();
//        }
//        if(product != null) {
////            Cart cart = Utils.getCartInSession(request);
//            Cart cart = Utils.getCartInSession(request);
//            Product newProduct = new Product(product);
//            cart.addProduct(newProduct, 1);
//        }
//        return "redirect:/shoppingCart";
//    }
//    @GetMapping("/removeCart/{id}")
//    public String removeCart(HttpServletRequest request, Model model,
//                             @RequestParam(value = "id", defaultValue = "") Long id) {
//        Product product = null;
//        if(id != null) {
//            product = productService.findById(id).get();
//        }
//        if(product != null) {
//            Cart cart = Utils.getCartInSession(request);
//            Product newProduct = new Product(product);
//            cart.removeProduct(newProduct);
//        }
//        return "redirect:/shoppingCart";
//    }
//    @RequestMapping(value = {"/shoppingCart"}, method = RequestMethod.POST)
//    public String cartUpdateQuantity(HttpServletRequest request, Model model,
//                                     @ModelAttribute("cart") Cart cart) {
//        Cart newCart = Utils.getCartInSession(request);
//        cart.updateQuantity(cart);
//        return "redirect:/shoppingCart";
//    }
//    @RequestMapping(value ={ "/shoppingCart"}, method = RequestMethod.GET)
//    public String getCart(HttpServletRequest request, Model model) {
//        Cart carts = Utils.getCartInSession(request);
//        model.addAttribute("carts", carts);
//        return "cart";
//    }
//
//}
