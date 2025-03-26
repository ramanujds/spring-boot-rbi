package com.rbi.myspringbootapp.api;

import com.rbi.myspringbootapp.dto.Message;
import com.rbi.myspringbootapp.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MyRestController {

    private MessageService service;

    public MyRestController(MessageService service) {
        this.service = service;
    }

    @GetMapping
    public String sayHello(){
        return "Hello from Spring Boot";
    }

    @GetMapping("/message")
    public Message fetchMessage(){
       return service.fetchGoodMorningMessage();
    }


}
