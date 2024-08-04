package com.SpringBoot.SpringSecurity.Service;

import com.SpringBoot.SpringSecurity.Config.JWTUtils;
import com.SpringBoot.SpringSecurity.Model.Login;
import com.SpringBoot.SpringSecurity.Model.Register;
import com.SpringBoot.SpringSecurity.Model.Role;
import com.SpringBoot.SpringSecurity.Model.User;
import com.SpringBoot.SpringSecurity.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager AuthManager;
    private final JWTUtils jwtUtils;

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

    public ResponseEntity<?> login(Login login){

        try {
            Authentication authentication = AuthManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(" UserName OR Password Invalid .....! ");
        }

        Optional<User> user = userRepository.findByEmail(login.getEmail());
        if (user.isPresent()){
            String Token = jwtUtils.generateToken(user.get());
        return ResponseEntity.ok(Token);
        } else {

            return ResponseEntity.noContent().build();

        }

    }

    public ResponseEntity<User> update(int id, String role) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (role.equals("DEVELOPER")) {
                user.get().setRole(Role.DEVOLEPER);
            } else if (role.equals("ADMIN")) {
                user.get().setRole(Role.ADMIN);
            } else {
                user.get().setRole(Role.USER);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user.get()));
        } else {
            return ResponseEntity.noContent().build();
        }

    }

}

