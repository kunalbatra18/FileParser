package com.example.file.parser.config;

import com.example.file.parser.dto.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class FileConfig {

    @Bean
    public BlockingQueue<Order> producer(){
        return new LinkedBlockingQueue<Order>();
    }
}
