//package com.example.se2project.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//public class Cart {
//    @Id
//    @Column(name = "id", nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
////    @OneToOne
////    @JoinColumn(name = "order_id")
////    private Order order;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToMany
//    private final List<CartProduct> cartProductList = new ArrayList<CartProduct>();
//
//    public CartProduct findProductById(Long id) {
//        for(CartProduct cartProduct: this.cartProductList) {
//            if(cartProduct.getProduct().getProductId().equals(id)) {
//                return cartProduct;
//            }
//        }
//        return null;
//    }
//    public void addProduct(Product product, int quantity) {
//        CartProduct cartProduct = this.findProductById(product.getProductId());
//        if(cartProduct == null) {
//            cartProduct = CartProduct.builder().quantity(0).product(product).build();
//            this.cartProductList.add(cartProduct);
//        }
//        int updateQuantity = cartProduct.getQuantity() + quantity;
//        if(updateQuantity <= 0) {
//            this.cartProductList.remove(cartProduct);
//        }else {
//            cartProduct.setQuantity(updateQuantity);
//        }
//    }
//    public void updateProduct(Long id, int quantity) {
//        CartProduct cartProduct = this.findProductById(id);
//        if(cartProduct != null) {
//            if(quantity <= 0) {
//                this.cartProductList.remove(cartProduct);
//            }else {
//                cartProduct.setQuantity(quantity);
//            }
//        }
//    }
//    public void removeProduct(Product product) {
//        CartProduct cartProduct = this.findProductById(id);
//        if(cartProduct != null) {
//            this.cartProductList.remove(cartProduct);
//        }
//    }
//    public int getAllQuantity() {
//        int quantity = 0;
//        for(CartProduct cartProduct: this.cartProductList) {
//            quantity += cartProduct.getQuantity();
//        }
//        return quantity;
//    }
//    public double getTotalPrice() {
//        double total = 0;
//        for(CartProduct cartProduct: this.cartProductList) {
//            total += cartProduct.getTotalPrice();
//        }
//        return total;
//    }
//    public void updateQuantity(Cart cart) {
//        if (cart != null) {
//            List<CartProduct> cartProducts = cart.getCartProductList();
//            for (CartProduct cartProduct : cartProducts) {
//                this.updateProduct(cartProduct.getProduct().getProductId(), cartProduct.getQuantity());
//            }
//        }
//    }
//}
