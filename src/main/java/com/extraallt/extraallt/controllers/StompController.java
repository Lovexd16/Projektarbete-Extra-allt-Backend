package com.extraallt.extraallt.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("*")
public class StompController {

    // Metod för att skicka meddelanden i chatt
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public String sendChatMessage(@Payload String message) {
        return message;
    }
}
