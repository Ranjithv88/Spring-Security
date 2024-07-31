package com.SpringBoot.SpringSecurity.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Register {

    private String name;
    private String email;
    private String password;
    private long number;

}

