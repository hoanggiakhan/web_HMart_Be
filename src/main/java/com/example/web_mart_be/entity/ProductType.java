package com.example.web_mart_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Data
@Table(name = "producttype")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_type")
    private int idProductType;
    @Column(name = "name_product_type")
    private String nameProductType;
    @ManyToMany(fetch = FetchType.LAZY , cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinTable(
            name = "product_producttype",
            joinColumns = @JoinColumn(name = "id_product_type"),
            inverseJoinColumns = @JoinColumn(name = "id_product")
    )
    private List<Product> listProducts;
}
