package com.rbi.myspringbootapp.api;

import com.rbi.myspringbootapp.dto.Message;
import com.rbi.myspringbootapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MyRestController {

    private MessageService messageService;

    public MyRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/hello")
    public Message sayHello(){
        String content = messageService.generateMessage();
        Message msg = new Message(content,"John",LocalDateTime.now());
        return msg;
    }


}
