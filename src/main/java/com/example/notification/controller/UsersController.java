package com.example.notification.controller;

import com.example.notification.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RestController
public class UsersController {

    @Autowired
    private UsersServiceImpl usersService;

    @GetMapping(value = "/test")
    public ResponseEntity test() {
        System.out.println("get cp_send ");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity getAllUser() {
        System.out.println("get cp_send ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 8, 0, 0);
        LocalDateTime end = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
        String endString = end.format(formatter);
        String startString = start.format(formatter);
        return new ResponseEntity(usersService.getAllUser(startString, endString), HttpStatus.OK);
    }

}
