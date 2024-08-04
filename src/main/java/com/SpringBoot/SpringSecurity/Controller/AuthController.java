package com.SpringBoot.SpringSecurity.Controller;

import com.SpringBoot.SpringSecurity.Model.Login;
import com.SpringBoot.SpringSecurity.Model.Register;
import com.SpringBoot.SpringSecurity.Model.User;
import com.SpringBoot.SpringSecurity.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @GetMapping("/test")
    public String testApi(){
        return "TestApi works.";
    }

    @PostMapping("/register")
    public ResponseEntity<String> PostApiForRegister(@RequestBody Register register){
        return ResponseEntity.ok().body(service.RegisterService(register));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return service.login(login);
    }

    @GetMapping("/{id}/{role}")
    public ResponseEntity<User> updateRole(@PathVariable int id, @PathVariable String role){
        return service.update(id, role);
    }

}

