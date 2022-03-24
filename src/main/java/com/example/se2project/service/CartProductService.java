package com.example.se2project.service;

import com.example.se2project.entity.CartProduct;

import java.util.List;

public interface CartProductService extends BaseService<CartProduct, Long>{
//    void insert(CartProduct cartProduct);
//
//    void update(Long productId, int quantity);
//
    List<CartProduct> getCartProduct(Long userId);
//    double getTotalPrice();

//    int getCartProductCount();
}
