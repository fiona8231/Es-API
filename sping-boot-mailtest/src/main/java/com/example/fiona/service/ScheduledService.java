package com.example.fiona.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Service
public class ScheduledService implements WebMvcConfigurer {

    @Scheduled(cron = "30 27 13 * * ?")
    public void hello(){


        System.out.println("你被执行了");
    }
}
