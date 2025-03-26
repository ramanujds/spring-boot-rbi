package com.rbi.myspringapp.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    public void sendMessage(String message){
        System.out.println("Message sent : ");
        System.out.println("Content : "+message);
    }

}
