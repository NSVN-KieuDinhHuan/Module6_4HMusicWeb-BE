package com.codegym.g2m6appmusicbe.model.dto;

import com.codegym.g2m6appmusicbe.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {
    @Size(min = 5, max = 20, message = "Tên sản phẩm phải từ 5 -> 20 ký tự")
    private String username;
    @NotNull
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    @Size(min = 5, max = 20, message = "Tên sản phẩm phải từ 5 -> 20 ký tự")
    private String address;
    private MultipartFile image;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private List<Role> roles;

}