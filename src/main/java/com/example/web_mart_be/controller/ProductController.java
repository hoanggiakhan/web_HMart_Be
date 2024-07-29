package com.example.web_mart_be.controller;

import com.example.web_mart_be.service.product.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(path = "/add-product")
    public ResponseEntity<?> save(@RequestBody JsonNode jsonData) {
        try {
            return productService.save(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi r");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/update-product")
    public ResponseEntity<?> update(@RequestBody JsonNode jsonData) {
        try{
            return productService.update(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi r");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/get-total")
    public long getTotal() {
        return productService.getTotalProduct();
    }
}
