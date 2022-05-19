package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.dto.ChangePasswordForm;
import com.codegym.g2m6appmusicbe.model.dto.JwtResponse;
import com.codegym.g2m6appmusicbe.model.dto.SignUpForm;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.JwtService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

//@RequestMapping("/user")
@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtService jwtService;

    @Value("${file-upload}")
    private String uploadPath;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        //Kiểm tra username và pass có đúng hay không
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        //Lưu user đang đăng nhập vào trong context của security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(currentUser.getId(), jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody SignUpForm user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user0 = new User(
                user.getUsername(),
                user.getPassword(),
                user.getConfirmPassword(),
                user.getRoles(),
                user.getPhoneNumber(),
                user.getAddress());
        return new ResponseEntity<>(userService.save(user0), HttpStatus.CREATED);
    }

    @PostMapping("/register2")
    public ResponseEntity<User> registerImage(@ModelAttribute SignUpForm signUpForm) {
        MultipartFile userImage = signUpForm.getImage();
        String imageName = "";
        if (!signUpForm.getPassword().equals(signUpForm.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userImage.getSize() != 0) {
            imageName = signUpForm.getImage().getOriginalFilename();
            imageName = System.currentTimeMillis() + imageName;
            try {
                FileCopyUtils.copy(signUpForm.getImage().getBytes(), new File(uploadPath + imageName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User user1 = new User(
                signUpForm.getUsername(),
                signUpForm.getPassword(),
                signUpForm.getConfirmPassword(),
                signUpForm.getRoles(),
                signUpForm.getPhoneNumber(),
                signUpForm.getAddress(),
                imageName
        );
        return new ResponseEntity<>(userService.save(user1), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User editUser) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        editUser.setId(id);
        return new ResponseEntity<>(userService.save(editUser), HttpStatus.OK);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<User> updateUser2(@PathVariable Long id, @ModelAttribute SignUpForm newSignUpForm) {
        Optional<User> oldUser = userService.findById(id);
        if (!oldUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MultipartFile multipartFile = newSignUpForm.getImage();
        String fileName;
        if (multipartFile == null) {
            fileName = oldUser.get().getImage();
        } else {
            fileName = multipartFile.getOriginalFilename();
            fileName = System.currentTimeMillis() + fileName;
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User newUser = new User(
                newSignUpForm.getUsername(),
                newSignUpForm.getRoles(),
                newSignUpForm.getPhoneNumber(),
                newSignUpForm.getAddress(),
                fileName);
        newUser.setId(id);
        newUser.setRoles(newSignUpForm.getRoles());
        newUser.setPassword(oldUser.get().getPassword());
        newUser.setConfirmPassword((oldUser.get().getConfirmPassword()));
        return new ResponseEntity<>(userService.save(newUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.removeById(id);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Iterable<User>> getAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordForm changePasswordForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        changePasswordForm.getUsername(),
                        changePasswordForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findByUsername(changePasswordForm.getUsername());
        if (changePasswordForm.getPassword().equals(changePasswordForm.getNewPassword())||
                !changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(changePasswordForm.getNewPassword());
        user.setConfirmPassword(changePasswordForm.getConfirmPassword());
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
}

