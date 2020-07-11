package ru.stepan.ponomarev.storage_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class StorageProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageProjectApplication.class, args);
    }

}
