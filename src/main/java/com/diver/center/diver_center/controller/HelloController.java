package com.diver.center.diver_center.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {


    @GetMapping
    public String sayHello(){
        return "hello from hello controller";
    }

}
