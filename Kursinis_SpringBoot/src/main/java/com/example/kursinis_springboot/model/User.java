package com.example.kursinis_springboot.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true)
    String login;
    String password;
    LocalDate birthDate;
    String name;
    String surname;

    public User(String login, String password, LocalDate birthDate, String name, String surname) {
        this.login = login;
        this.password = password;
        this.birthDate = birthDate;
        this.name = name;
        this.surname = surname;
    }

    public User(int id, String login, String password, LocalDate birthDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.birthDate = birthDate;
    }

    public User(int id, String password, String name, String surname) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
