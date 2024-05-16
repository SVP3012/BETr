package com.example.betr_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BetrBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetrBackendApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value="Pannu", defaultValue="world") String name){
        return String.format("Hello %s! good evening", name);
    }
}