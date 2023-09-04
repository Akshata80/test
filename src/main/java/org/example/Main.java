package org.example;

import org.example.model.User;
import org.example.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@ComponentScan(basePackages = "org.example")
public class Main {
    @Autowired
    private AuthRepository authrepository;

    @PostConstruct
    public void initUsers() {
        User users = new User(101, "user", "password", "user@gmail.com");
        authrepository.save(users);
    }

    public static void main(String[] args) {
        System.out.print("Application Started...!!!");
        SpringApplication.run(Main.class, args);
    }
}