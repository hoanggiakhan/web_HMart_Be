package com.example.web_mart_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_detail")
    private long idOrderDetail; // Mã chi tiết đơn hàng
    @Column(name = "quantity")
    private int quantity; // Số lượng
    @Column(name = "price")
    private double price; // Giá của 1 sản phẩm


    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "id_product", nullable = false)
    private Product product; // sản phẩm

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_order", nullable = false)
    private Order order; // Đơn hàng
}
