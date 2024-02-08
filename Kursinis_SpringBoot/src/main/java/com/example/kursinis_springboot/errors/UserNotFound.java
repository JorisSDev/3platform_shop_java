package com.example.kursinis_springboot.errors;

public class UserNotFound extends RuntimeException {
    public UserNotFound(int id) {
        super("User with id [" + id + "] not found");
    }
}
