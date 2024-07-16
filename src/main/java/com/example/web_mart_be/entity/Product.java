package com.example.web_mart_be.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int idProduct; // mã sản phẩm
    @Column(name = "name_product")
    private String nameProduct; // tên sản phẩm
    @Column(name = "description" , columnDefinition = "text")
    private String description; // mô tả
    @Column(name = "list_price")
    private double listPrice; // giá niêm yết
    @Column(name = "sell_price")
    private double sellPrice; // giá bán
    @Column(name = "quantity")
    private int quantity; // số lượng
    @Column(name = "unit")
    private String unit;  // đơn vị tính
    @Column(name = "sold_quantity")
    private int soldQuantity;
    @ManyToMany(fetch = FetchType.LAZY , cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinTable(
            name = "product_supplier",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_supplier")
    )
    private List<Supplier> listSuppliers;
    @ManyToMany(fetch = FetchType.LAZY , cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinTable(
            name = "product_producttype",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_product_type")
    )
    private List<ProductType> listProductTypes;
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    private List<Image> listImages;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    private List<OrderDetail> listOrderDetails;

}
