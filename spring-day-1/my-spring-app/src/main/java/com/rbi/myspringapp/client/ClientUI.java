package com.rbi.myspringapp.client;

import com.rbi.myspringapp.service.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ClientUI {


    private MessageSenderService service;
    private Scanner scanner;


    public ClientUI(MessageSenderService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void send(){
        System.out.println("Type a message");
        String message = scanner.nextLine();
        service.sendMessage(message);
    }

}
