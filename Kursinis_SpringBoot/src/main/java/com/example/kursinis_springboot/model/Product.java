package com.example.kursinis_springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String title;
    String description;
    String manufacturer;
    Double price;

    @JsonIgnore
    @ManyToOne
    Warehouse warehouse;

    @JsonIgnore
    @ManyToOne
    Cart cart;

    public Product(String title, String description, String manufacturer, Double price, Warehouse warehouse) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
        this.warehouse = warehouse;
    }

    @Override
    public String toString() {
        return title + "/" + description + "/" + manufacturer + "/" + price + "/" + warehouse;
    }
}
