package com.example.web_mart_be.dao;

import com.example.web_mart_be.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "product-type")
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
}
