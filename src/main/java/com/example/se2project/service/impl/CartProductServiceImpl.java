package com.example.se2project.service.impl;

import com.example.se2project.entity.CartProduct;
import com.example.se2project.repository.CartProductRepository;
import com.example.se2project.repository.UserRepository;
import com.example.se2project.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductServiceImpl extends BaseServiceImpl<CartProduct, Long, CartProductRepository> implements CartProductService {
    @Autowired
    CartProductRepository cartProductRepository;
    @Autowired
    UserRepository userRepository;
//    @Override
//    public void insert(CartProduct cartProduct) {
//        CartProduct cartProductExisted = cartProductRepository.getById(cartProduct.getProductID());
//
//        cartProductExisted.setQuantity(cartProduct.getQuantity() + cartProductExisted.getQuantity()) ;
//
//    }
//    @Override
//    public void update(Long productId, int quantity) {
//        CartProduct cartProduct = cartProductRepository.getById(productId);
//        cartProduct.setQuantity(quantity + cartProduct.getQuantity());
//        if(cartProduct.getQuantity() <= 0) {
//            cartProductRepository.deleteById(productId);
//        }
//    }

    @Override
    public List<CartProduct> getCartProduct(Long userId) {
        return cartProductRepository.findByUser_UserId(userId);
    }

//    @Override
//    public double getTotalPrice() {
//        return cartProductRepository.findAll().stream().mapToDouble(product -> product.getQuantity() * product.getPrice()).sum();
//    }
//
//    @Override
//    public int getCartProductCount() {
//        if(cartProductRepository.findAll().isEmpty())
//            return 0;
//        return cartProductRepository.findAll().size();
//    }

}
