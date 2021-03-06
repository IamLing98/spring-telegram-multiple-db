package com.example.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication

public class NotificationApplication {

    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

}
