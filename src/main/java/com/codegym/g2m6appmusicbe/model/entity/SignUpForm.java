package com.codegym.g2m6appmusicbe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {
    private String username;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String address;
    private String image;
}
