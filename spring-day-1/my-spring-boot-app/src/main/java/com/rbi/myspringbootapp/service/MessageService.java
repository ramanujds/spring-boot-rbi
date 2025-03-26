package com.rbi.myspringbootapp.service;

import com.rbi.myspringbootapp.dto.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    public Message fetchGoodMorningMessage(){
        return new Message("Good Morning","John", LocalDateTime.now());
    }

    public Message fetchGoodNightMessage(){
        return new Message("Good Night","Steve",LocalDateTime.now());
    }

}
