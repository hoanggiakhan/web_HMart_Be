package com.example.web_mart_be.dao;

import com.example.web_mart_be.entity.Image;
import com.example.web_mart_be.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "images")
public interface ImageRepository extends JpaRepository<Image,Integer> {
    public List<Image> findImagesByProduct(Product product);
    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.isIcon = false AND i.product.idProduct = :productId")
    public void deleteImagesWithFalseIconByProductId(@Param("productId") int bookId);
}
