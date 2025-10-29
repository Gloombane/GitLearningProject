package com.example.gitlearningprojectwithspringandreact.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
    @GetMapping("/attack")
    public String showAttackPage() {
        // attackTest.html must be in src/main/resources/templates/
        return "attackTest"; // Thymeleaf will render attackTest.html
    }
    @PostMapping("/transfer-money")
    public String transferMoney(@RequestParam String to, @RequestParam int amount) {
        // Simulate a money transfer
        System.out.println("💸 Transferring " + amount + " to " + to);
        return "Money sent to " + to + " (amount: " + amount + ")";
    }
    @GetMapping("/test")
    public String getTestPage() {

        return "test";
    }

}
