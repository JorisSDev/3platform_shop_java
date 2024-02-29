package com.example.kursinis_android.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Motherboard extends Product {
    String socketType;
    String formFactor;

    String RAMType;

    public Motherboard(String title, String description, String manufacturer, Double price, Warehouse warehouse, String socketType, String formFactor, String RAMType) {
        super(title, description, manufacturer, price, warehouse);
        this.socketType = socketType;
        this.formFactor = formFactor;
        this.RAMType = RAMType;
    }

    public String toString() {
        return super.toString() + "/" + socketType + "/" + formFactor + "/" + RAMType;
    }
}
