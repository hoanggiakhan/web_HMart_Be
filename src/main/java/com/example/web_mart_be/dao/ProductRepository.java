package com.example.web_mart_be.dao;

import com.example.web_mart_be.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(path = "products")
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Page<Product> findByNameProductContaining(@RequestParam("nameProduct") String nameProduct, Pageable pageable);
    Page<Product> findByListProductTypes_IdProductType(@RequestParam("idProductType") int idProductType, Pageable pageable);
    Page<Product> findByNameProductContainingAndListProductTypes_IdProductType(@RequestParam("nameProduct") String nameProduct , @RequestParam("idProductType") int idProductType, Pageable pageable);
}
