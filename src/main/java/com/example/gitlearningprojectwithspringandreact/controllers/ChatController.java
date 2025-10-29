package com.example.gitlearningprojectwithspringandreact.controllers;

import com.example.gitlearningprojectwithspringandreact.entities.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Controller
public class ChatController {

    // 🔹 Обрабатывает сообщения от клиентов
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        // просто возвращаем сообщение, чтобы брокер разослал всем подписчикам
        return message;
    }

    // 🔹 Обрабатывает событие "новый пользователь подключился"
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/messages")
    public ChatMessage addUser(ChatMessage message) {
        message.setType("JOIN");
        message.setContent(message.getSender() + " подключился к чату");
        return message;
    }
}
