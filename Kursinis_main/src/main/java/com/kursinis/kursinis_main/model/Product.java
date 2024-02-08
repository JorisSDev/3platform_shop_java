package com.kursinis.kursinis_main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.List;

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

    @ManyToOne
    Warehouse warehouse;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Review> reviews;

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
