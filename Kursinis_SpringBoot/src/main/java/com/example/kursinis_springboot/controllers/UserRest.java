package com.example.kursinis_springboot.controllers;

import com.example.kursinis_springboot.errors.UserNotFound;
import com.example.kursinis_springboot.model.Customer;
import com.example.kursinis_springboot.model.Manager;
import com.example.kursinis_springboot.model.User;
import com.example.kursinis_springboot.model.UserCredentials;
import com.example.kursinis_springboot.repos.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserRest {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value="/user/getAll")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value="/user/getById/{id}")
    public @ResponseBody User getUserById(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
    }
    @PostMapping(value="/user/insert")
    public @ResponseBody User inserteUser(@RequestBody User user) {
        return userRepository.saveAndFlush(user);
    }

    @PostMapping(value="/user/validate")
    public @ResponseBody User validate(@RequestBody UserCredentials credentials) {
        return userRepository.getUserByLoginAndPassword(credentials.getLogin(), credentials.getPassword());
    }
    @PutMapping(value="user/update")
    public @ResponseBody Optional<User> updateUserById(@RequestBody Manager manager) {

        User dbUser = userRepository.findById(manager.getId()).orElseThrow(() -> new UserNotFound(manager.getId()));

        dbUser.setName(manager.getName());
        dbUser.setPassword(manager.getPassword());

        userRepository.save(dbUser);
        return userRepository.findById(manager.getId());
    }

    @DeleteMapping(value="/user/delete/{id}")
    public @ResponseBody String deleteUserById(@PathVariable int id) {
        userRepository.deleteById(id);
        return "User deleted";
    }

//    @GetMapping(value="/getUserById/{id}")
//    public EntityModel<User> one(@PathVariable int id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
//        return EntityModel.of(user, linkTo(methodOn(UserRest.class, one(id))).withSelfRel(), linkTo(methodOn(UserRest.class, getAllUsers())).withRel("Users"));
//    }


}
