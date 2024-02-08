package com.kursinis.kursinis_main.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CPU extends Product {
    String architecture;
    Boolean isAPU;

    double clockspeed_GHz;
    int coreCount;

    public CPU(String title, String description, String manufacturer, Double price, Warehouse warehouse, String architecture, double clockspeed_GHz, int coreCount, Boolean isAPU) {
        super(title, description, manufacturer, price, warehouse);
        this.architecture = architecture;
        this.clockspeed_GHz = clockspeed_GHz;
        this.coreCount = coreCount;
        this.isAPU = isAPU;
    }

    @Override
    public String toString() {
        return super.toString() + "/" + architecture + "/" + isAPU + "/" + clockspeed_GHz + "/" + coreCount;
    }


}
