package com.kursinis.kursinis_main.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> itemsInCart;

    @ManyToOne
    private User owner;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", dateCreated=" + dateCreated +
                ", owner=" + owner.getId() +
                '}';
    }

    public void setItemsInCart(List<Product> itemsInCart) {
        this.itemsInCart = itemsInCart;
        System.out.println("itemsInCart: " + this.itemsInCart);
    }

    public Cart(User owner) {
        this.owner = owner;
        this.dateCreated = LocalDateTime.now();
        this.itemsInCart = new ArrayList<>();
    }


    public Cart() {
        this.dateCreated = LocalDateTime.now();
    }

}
