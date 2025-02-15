package com.example.web_mart_be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private int idCart;
    @Column (name = "quantity")
    private int quantity;
    @ManyToOne()
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;
    @ManyToOne()
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
}
