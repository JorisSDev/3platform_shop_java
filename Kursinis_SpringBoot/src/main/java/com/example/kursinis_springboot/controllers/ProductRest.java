package com.example.kursinis_springboot.controllers;

import com.example.kursinis_springboot.model.Product;
import com.example.kursinis_springboot.repos.ProductRepository;
import com.example.kursinis_springboot.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductRest {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value="/product/getAll")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(value="/product/getById/{id}")
    public @ResponseBody Product getProductById(@PathVariable int id) {
        return productRepository.findById(id).orElseThrow();
    }

    @PostMapping(value="/product/insert")
    public @ResponseBody Product saveProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @PutMapping(value="/product/update")
    public @ResponseBody Product updateProductById(@RequestBody Product product) {
        Product dbProduct = productRepository.findById(product.getId()).orElseThrow();

        dbProduct.setTitle(product.getTitle());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setDescription(product.getDescription());

        productRepository.save(dbProduct);
        return productRepository.findById(product.getId()).orElseThrow();
    }

    @DeleteMapping(value="/product/delete/{id}")
    public @ResponseBody String deleteProductById(@PathVariable int id) {
        productRepository.deleteById(id);
        return "Product deleted";
    }


}
