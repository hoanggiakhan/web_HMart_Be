package com.example.web_mart_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Data
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplier")
    private int idSupplier;
    @Column(name = "name_supplier")
    private String nameSupplier;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "supplier_address")
    private String supplierAddress;
    @Column(name = "email")
    private String email;
    @ManyToMany(fetch = FetchType.LAZY , cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinTable(
            name = "product_supplier",
            joinColumns = @JoinColumn(name = "id_supplier"),
            inverseJoinColumns = @JoinColumn(name = "id_product")
    )
    private List<Product> listProducts;
}
