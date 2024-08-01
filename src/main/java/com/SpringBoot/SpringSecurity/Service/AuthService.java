package com.SpringBoot.SpringSecurity.Service;

import com.SpringBoot.SpringSecurity.Config.Jwt;
import com.SpringBoot.SpringSecurity.Model.Login;
import com.SpringBoot.SpringSecurity.Model.Register;
import com.SpringBoot.SpringSecurity.Model.Role;
import com.SpringBoot.SpringSecurity.Model.User;
import com.SpringBoot.SpringSecurity.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager AuthManager;
    private final Jwt jwt;

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

        Optional<User> user = userRepository.FindByEmail(login.getEmail());
        if (user.isPresent()){
            String Token = jwt.TokenG(user.get());
        return ResponseEntity.ok(Token);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

}

