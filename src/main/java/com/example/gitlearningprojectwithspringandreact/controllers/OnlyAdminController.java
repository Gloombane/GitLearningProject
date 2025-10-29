package com.example.gitlearningprojectwithspringandreact.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class OnlyAdminController {

    @GetMapping("/adminTest")
    public String adminTest() {
        return "Эта страница доступна только ADMIN";
    }
}
