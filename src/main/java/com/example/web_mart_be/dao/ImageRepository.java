package com.example.web_mart_be.dao;

import com.example.web_mart_be.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "images")
public interface ImageRepository extends JpaRepository<Image,Integer> {

}
