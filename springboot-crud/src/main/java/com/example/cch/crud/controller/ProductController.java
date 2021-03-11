package com.example.cch.crud.controller;

import java.util.List;

import com.example.cch.crud.model.Product;
import com.example.cch.crud.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> list() {
        return productService.getAllProduct();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> get(@PathVariable(value = "id") Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null){
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products")
    public void add(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> update(@RequestBody Product product, @PathVariable(value = "id") Long id){
        try {
            Product existProduct = productService.getProductById(id);
            if (existProduct == null){
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
            }
            productService.saveProduct(product);
            return new ResponseEntity<Product>(HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        productService.deleteProductById(id);
    }
}
