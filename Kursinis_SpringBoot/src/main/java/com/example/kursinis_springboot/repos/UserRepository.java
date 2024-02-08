package com.example.kursinis_springboot.repos;

import com.example.kursinis_springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User getUserByLoginAndPassword(String login, String psw);
}
