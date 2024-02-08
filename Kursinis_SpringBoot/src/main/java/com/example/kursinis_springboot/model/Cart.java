package com.example.kursinis_springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateCreated;

    @JsonIgnore
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> itemsInCart;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                '}';
    }

    public void setItemsInCart(List<Product> itemsInCart) {
        this.itemsInCart = itemsInCart;
        System.out.println("itemsInCart: " + this.itemsInCart);
    }

    public Cart(List<Product> itemsInCart) {
        this.dateCreated = LocalDateTime.now();
        this.itemsInCart = itemsInCart;
    }


    public Cart() {
        this.dateCreated = LocalDateTime.now();
    }
}
