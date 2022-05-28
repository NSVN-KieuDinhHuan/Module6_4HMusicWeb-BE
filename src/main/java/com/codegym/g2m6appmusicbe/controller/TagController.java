package com.codegym.g2m6appmusicbe.controller;


import com.codegym.g2m6appmusicbe.model.entity.Tag;


import com.codegym.g2m6appmusicbe.service.tag.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@CrossOrigin("*")
@RequestMapping("/tags")
public class TagController {
    @Autowired
    public ITagService tagService;
    @GetMapping
    public ResponseEntity<Iterable<Tag>> getAll(){
        Iterable<Tag> tags = tagService.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tag>findById(@PathVariable Long id){
        Optional<Tag> tagOptional = tagService.findById(id);
        return new ResponseEntity<>(tagOptional.get(), HttpStatus.OK);
    }
}

