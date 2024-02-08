package com.example.kursinis_springboot.repos;

import com.example.kursinis_springboot.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
