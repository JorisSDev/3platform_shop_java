package com.example.kursinis_springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonIgnore
    @ManyToMany(mappedBy = "worksAtWarehouse", cascade = CascadeType.ALL)
    private List<Manager> managers;
    @JsonIgnore
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Product> inStockProducts;

    public Warehouse(String title, String address) {
        this.title = title;
        this.address = address;
        this.inStockProducts = new ArrayList<>();
        this.managers = new ArrayList<>();
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

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    public void setInStockProducts(List<Product> inStockProducts) {
        this.inStockProducts = inStockProducts;
    }
}
