package com.example.cch.crud.service;

import java.util.List;

import com.example.cch.crud.model.Product;

public interface ProductService {
    List<Product> getAllProduct();
    void saveProduct(Product product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
}
