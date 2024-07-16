package com.example.web_mart_be.dao;

import com.example.web_mart_be.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "suppliers")
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
