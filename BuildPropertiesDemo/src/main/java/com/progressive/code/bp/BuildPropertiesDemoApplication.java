package com.progressive.code.bp;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;

@SpringBootApplication
public class BuildPropertiesDemoApplication implements CommandLineRunner {

    @Autowired
    private BuildProperties buildProperties;

    public static void main(String[] args) {
        SpringApplication.run(BuildPropertiesDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(String.format("Version: %s Time: %s", buildProperties.getVersion(), buildProperties.getTime()));
        System.out.println("Current Time is: " + LocalDateTime.now());
    }

}
