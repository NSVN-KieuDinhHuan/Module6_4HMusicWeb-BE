package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.entity.Role;
import com.codegym.g2m6appmusicbe.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<Iterable<Role>> getAllRole() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
}
