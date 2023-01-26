package com.github.youtubeAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class YoutubeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeApiApplication.class, args);
    }
}
