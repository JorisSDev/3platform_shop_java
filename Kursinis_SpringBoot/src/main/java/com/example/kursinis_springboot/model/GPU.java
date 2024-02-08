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
public class GPU extends Product {
    double clockSpeed_MHz;
    int VRAMSize_GB;
    int powerConsumption_W;

    public GPU(String title, String description, String manufacturer, Double price, Warehouse warehouse, double clockSpeed_MHz, int VRAMSize_GB, int powerConsumption_W) {
        super(title, description, manufacturer, price, warehouse);
        this.clockSpeed_MHz = clockSpeed_MHz;
        this.VRAMSize_GB = VRAMSize_GB;
        this.powerConsumption_W = powerConsumption_W;
    }

    @Override
    public String toString() {
        return super.toString() + "/" + clockSpeed_MHz + "/" + VRAMSize_GB + "/" + powerConsumption_W;
    }
}
