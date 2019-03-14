package com.example.room;

import com.example.room.utils.common.UUIDGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class RoomApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomApplication.class, args);
    }
}

