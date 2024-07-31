package com.SpringBoot.SpringSecurity.Controller;

import com.SpringBoot.SpringSecurity.Model.Register;
import com.SpringBoot.SpringSecurity.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Security/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("register")
    public ResponseEntity<String> PostApiForRegister(@RequestBody Register register){
        return ResponseEntity.ok().body(service.RegisterService(register));
    }

}

