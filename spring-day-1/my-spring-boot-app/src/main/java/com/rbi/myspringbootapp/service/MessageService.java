package com.rbi.myspringbootapp.service;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class MessageService {


    public String generateMessage(){
        LocalTime time = LocalTime.now();
        if(time.isAfter(LocalTime.NOON)){
            return "Good Afternoon";
        }
        return "Good Morning";
    }


}


