package com.example.kursinis_springboot.repos;

import com.example.kursinis_springboot.model.Motherboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotherboardRepository extends JpaRepository<Motherboard, Integer> {
}
