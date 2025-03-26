package com.rbi.myspringapp.config;

import com.rbi.myspringapp.service.MessageSenderService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = "com.rbi.myspringapp")
@PropertySource("application.properties")
public class AppConfig {

    @Bean
    @Primary
    public MessageSenderService getMessageSender(){
        return new MessageSenderService();
    }

    @Bean
    public Scanner getScanner(){
        return new Scanner(System.in);
    }

}
