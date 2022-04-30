package com.example.se2project.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "cart_product")
public class CartProduct {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Product product = productrepo.findById(product_id);
     * C cart_id = userRepo.findById(user_1).getCart();
     * Cart cart = userRepo.findById(user.getCart())
     * CartProduct cartProduct = new CartProduct(1, cart, product, 5)
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;
    public double total = 0;

    public CartProduct(Product product, User user, int quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        Total(product, quantity);
    }

    public void Total(Product product, int quantity) {
        total += product.getPrice() * quantity;
    }

}
