package com.example.cch.crud.service;

import java.util.List;
import java.util.Optional;

import com.example.cch.crud.model.Product;
import com.example.cch.crud.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        // TODO Auto-generated method stub
        return this.productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        // TODO Auto-generated method stub
        productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        // TODO Auto-generated method stub
        Optional<Product> optional = productRepository.findById(id);
        Product product = null;
        if (optional.isPresent()){
            product = optional.get();
        }
        return product;
    }
    
    @Override
    public void deleteProductById(Long id) {
        // TODO Auto-generated method stub
        this.productRepository.deleteById(id);
    }
    
}
