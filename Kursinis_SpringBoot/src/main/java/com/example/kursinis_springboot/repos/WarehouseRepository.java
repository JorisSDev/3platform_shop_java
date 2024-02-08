package com.example.kursinis_springboot.repos;

import com.example.kursinis_springboot.model.User;
import com.example.kursinis_springboot.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}
