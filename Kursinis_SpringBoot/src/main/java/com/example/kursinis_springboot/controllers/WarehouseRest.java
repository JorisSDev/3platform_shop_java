package com.example.kursinis_springboot.controllers;

import com.example.kursinis_springboot.model.User;
import com.example.kursinis_springboot.model.Warehouse;
import com.example.kursinis_springboot.repos.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseRest {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping(value="/warehouse/getAll")
    public @ResponseBody Iterable<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @GetMapping(value="/warehouse/getById/{id}")
    public @ResponseBody Warehouse getWarehouseById(@PathVariable int id) {
        return warehouseRepository.findById(id).orElseThrow();
    }

    @PostMapping(value="/warehouse/insert")
    public @ResponseBody Warehouse insertWarehouse(Warehouse warehouse) {
        return warehouseRepository.saveAndFlush(warehouse);
    }

    @PostMapping(value="/warehouse/update")
    public @ResponseBody Warehouse updateWarehouseById(Warehouse warehouse) {
        return warehouseRepository.saveAndFlush(warehouse);
    }

    @DeleteMapping(value="/warehouse/delete/{id}")
    public @ResponseBody String deleteWarehouseById(@PathVariable int id) {
        warehouseRepository.deleteById(id);
        return "Warehouse deleted";
    }


}
