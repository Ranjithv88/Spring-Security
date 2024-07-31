package com.SpringBoot.SpringSecurity.Service;

import com.SpringBoot.SpringSecurity.Model.Register;
import com.SpringBoot.SpringSecurity.Model.Role;
import com.SpringBoot.SpringSecurity.Model.User;
import com.SpringBoot.SpringSecurity.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String RegisterService (Register register){
        var user = User.builder()
                .name(register.getName())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .number(register.getNumber())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return " Register Successfully .....! ";
    }

}

