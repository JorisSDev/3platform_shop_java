package com.kursinis.kursinis_main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Warehouse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String address;

    @ManyToOne
    private Manager managesWarehouse;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Product> inStockProducts;

    public Warehouse(String title, String address) {
        this.title = title;
        this.address = address;
        this.inStockProducts = new ArrayList<>();
    }

    public Warehouse(String title, String address, Manager managesWarehouse) {
        this.title = title;
        this.address = address;
        this.managesWarehouse = managesWarehouse;
        this.inStockProducts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInStockProducts(List<Product> inStockProducts) {
        this.inStockProducts = inStockProducts;
    }
}
