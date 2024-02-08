package com.example.kursinis_springboot.controllers;

import com.example.kursinis_springboot.model.Cart;
import com.example.kursinis_springboot.repos.CartRepository;
import com.example.kursinis_springboot.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartRest {
    @Autowired
    private CartRepository cartRepository;

    @GetMapping(value="/cart/getAll")
    public @ResponseBody Iterable<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @GetMapping(value="/cart/getById/{id}")
    public @ResponseBody Cart getCartById(@PathVariable int id) {
        return cartRepository.findById(id).orElseThrow();
    }

    @PostMapping(value="/cart/insert")
    public @ResponseBody Cart insertCart(Cart cart) {
        return cartRepository.saveAndFlush(cart);
    }

    @PostMapping(value="/cart/update")
    public @ResponseBody Cart updateCartById(Cart cart) {
        return cartRepository.saveAndFlush(cart);
    }

    @PostMapping(value="/cart/delete")
    public @ResponseBody String deleteCartById(int id) {
        cartRepository.deleteById(id);
        return "Cart deleted";
    }

}
