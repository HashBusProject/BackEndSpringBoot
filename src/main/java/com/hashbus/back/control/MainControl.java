package com.hashbus.back.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainControl {
    @GetMapping
    public String hello(){
        return "Hello There";
    }
}
