package com.example.se2project;

import com.example.se2project.entity.CartProduct;
import com.example.se2project.entity.Product;
import com.example.se2project.entity.User;
import com.example.se2project.repository.CartProductRepository;
import com.example.se2project.repository.ProductRepository;
import com.example.se2project.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class Se2ProjectApplicationTests {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartProductRepository cartProductRepository;
@Autowired
    UserRepository userRepository;
//    @Test
//    void contextLoads() {
//        Product product = productRepository.getById(1L);
//        User user = userRepository.getById(1L);
//        CartProduct cartProduct = new CartProduct();
//        cartProduct.setProduct(product);
//        cartProduct.setUser(user);
//        cartProduct.setQuantity(2);
//        CartProduct saveCartProduct = cartProductRepository.save(cartProduct);
//        Assertions.assertTrue(saveCartProduct.getId() > 0);
//    }
    @Test
    public void getCartProductByUser() {
//        User user = userRepository.findById(1L).get();
        List<CartProduct> cartProducts = cartProductRepository.findByUser_UserId(1L);
        Assertions.assertEquals(2, cartProducts.size());
    }
    @Test
    public void add() {
        String contextPath = "[[@{/}]]";
        int quantity = 1;
        int productId = 1;
        String url = "@{/" + "cart/addToCart/" + productId + "/" + quantity + "}";
        System.out.println(url);
    }
    @Test
    public void test() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "codejava";
        String encode = encoder.encode(rawPassword);
        System.out.println(encode);
    }
    @Test
    public void te() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "codejava";
        String encode = encoder.encode(rawPassword);
        User u = userRepository.findUserByEmailAndPassword("hatam@gmail.com", encode);
        System.out.println(u);
    }
    @Test
    public void testDate() {
        Period dayPlus = Period.ofDays(7);
        LocalDate date = LocalDate.now();
        LocalDate delivery = date.plus(dayPlus);
        java.sql.Date orderDate = Date.valueOf(date);
        java.sql.Date deliveryDate = Date.valueOf(delivery);
        System.out.println(orderDate);
        System.out.println(deliveryDate);
    }



}
