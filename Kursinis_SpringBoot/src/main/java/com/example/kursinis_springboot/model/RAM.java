package com.example.kursinis_springboot.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RAM extends Product {
    String type;
    int capacity_GB;
    int speed_MHz;

    public RAM(String title, String description, String manufacturer, Double price, Warehouse warehouse, String type, int capacity_GB, int speed_MHz) {
        super(title, description, manufacturer, price, warehouse);
        this.type = type;
        this.capacity_GB = capacity_GB;
        this.speed_MHz = speed_MHz;
    }

    public String toString() {
        return super.toString() + "/" + type + "/" + capacity_GB + "/" + speed_MHz;
    }
}
