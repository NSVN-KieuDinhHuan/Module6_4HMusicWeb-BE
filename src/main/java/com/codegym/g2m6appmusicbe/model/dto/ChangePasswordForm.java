package com.codegym.g2m6appmusicbe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordForm {
    private String username;
    private String password;
    private String newPassword;
}
