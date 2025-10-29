package com.example.gitlearningprojectwithspringandreact.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ChatMessage {
    private String sender;   // кто отправил
    private String content;  // текст сообщения
    private String type;     // тип сообщения (например, "CHAT", "JOIN", "LEAVE")


}
