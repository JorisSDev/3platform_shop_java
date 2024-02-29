package com.example.kursinis_android.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product implements Serializable {
    int id;

    String title;
    String description;
    String manufacturer;
    Double price;

    Warehouse warehouse;

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
