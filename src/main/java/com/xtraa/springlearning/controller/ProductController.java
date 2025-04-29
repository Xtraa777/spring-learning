package com.xtraa.springlearning.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public String getAllProducts() {
        System.out.println("GET /api/products");

        return "모든 상품 목록 반환";
    }

    @GetMapping("/{productId}")
    public String getProductById(@PathVariable Long productId) {
        System.out.println("GET /api/products/" + productId);

        return productId + " 번 상품 상세 정보 반환";
    }

    @PostMapping
    public String createProduct(@RequestBody Product product) {
        System.out.println("POST /api/products");

        return product.getName() + " 상품 생성 요청 받음";
    }

    @PutMapping("/{productId}")
    public String updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        System.out.println("PUT /api/products/" + productId + " 요청 받음");

        return productId + " 번 상품 정보 업데이트 요청 받음";
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        System.out.println("DELETE /api/products/" + productId + " 요청 받음");

        return productId + " 번 상품 삭제 요청 받음";
    }

    static class Product {

        private String name;
        private double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Product() {
        }
    }
}
