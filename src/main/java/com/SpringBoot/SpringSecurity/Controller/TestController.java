package com.SpringBoot.SpringSecurity.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TestController {

    @GetMapping("/user")
    public String userTest(){
        return " user api works ";
    }

    @GetMapping("/dev")
    public String devloperTest(){
        return " devloper api works ";
    }

    @GetMapping("/admin")
    public String adminTest(){
        return " admin api works ";
    }

}

