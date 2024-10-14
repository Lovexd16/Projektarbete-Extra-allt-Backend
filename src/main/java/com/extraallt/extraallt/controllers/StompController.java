package com.extraallt.extraallt.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.extraallt.extraallt.models.Chat;
import com.extraallt.extraallt.models.ChatMessage;
import com.extraallt.extraallt.models.Hello;
import com.extraallt.extraallt.models.HelloMessage;

@Controller
public class StompController {
     @MessageMapping("/greet")
    @SendTo("/topic/greeting")
    public Hello hello(HelloMessage message) {
        System.out.println("/Hello");
        return new Hello(message.getName() + " gick in i chatten!");
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Chat chat(ChatMessage chat) {
        System.out.println("/chat");
        return new Chat(chat.getContent());
    }
}
