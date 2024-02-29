package com.example.kursinis_android.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Cart {
    private int id;
    private LocalDateTime dateCreated;

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
