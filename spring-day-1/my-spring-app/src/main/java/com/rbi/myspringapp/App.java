package com.rbi.myspringapp;

import com.rbi.myspringapp.client.ClientUI;
import com.rbi.myspringapp.config.AppConfig;
import com.rbi.myspringapp.service.MessageSenderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ClientUI ui = context.getBean(ClientUI.class);

        ui.send();


    }

}
