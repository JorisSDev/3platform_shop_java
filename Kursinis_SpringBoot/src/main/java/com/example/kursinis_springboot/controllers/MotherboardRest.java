package com.example.kursinis_springboot.controllers;

import com.example.kursinis_springboot.model.Motherboard;
import com.example.kursinis_springboot.repos.MotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MotherboardRest {
    @Autowired
    private MotherboardRepository motherboardRepository;

    @GetMapping(value="/motherboard/getAll")
    public @ResponseBody Iterable<Motherboard> getAllMotherboards() {
        return motherboardRepository.findAll();
    }

    @GetMapping(value="/motherboard/getById/{id}")
    public @ResponseBody Motherboard getMotherboardById(@PathVariable int id) {
        return motherboardRepository.findById(id).orElseThrow();
    }

    @PostMapping(value="/motherboard/insert")
    public @ResponseBody Motherboard insertMotherboard(Motherboard motherboard) {
        return motherboardRepository.saveAndFlush(motherboard);
    }

    @PutMapping(value="/motherboard/update")
    public @ResponseBody Motherboard updateMotherboardById(Motherboard motherboard) {
        return motherboardRepository.saveAndFlush(motherboard);
    }

    @DeleteMapping(value="/motherboard/delete/{id}")
    public @ResponseBody String deleteMotherboardById(@PathVariable int id) {
        motherboardRepository.deleteById(id);
        return "Motherboard deleted";
    }
}
