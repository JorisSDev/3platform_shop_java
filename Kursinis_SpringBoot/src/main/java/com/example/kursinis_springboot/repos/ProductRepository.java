package com.example.kursinis_springboot.repos;

import com.example.kursinis_springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
