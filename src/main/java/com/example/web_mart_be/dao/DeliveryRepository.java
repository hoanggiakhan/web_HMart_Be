package com.example.web_mart_be.dao;

import com.example.web_mart_be.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "deliveries")
public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {
}
