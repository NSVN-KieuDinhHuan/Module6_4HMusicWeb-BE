package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.entity.Album;
import com.codegym.g2m6appmusicbe.model.entity.Category;
import com.codegym.g2m6appmusicbe.service.abum.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    public IAlbumService albumService;

    @GetMapping
    public ResponseEntity<Iterable<Album>> getAll(){
        Iterable<Album> albums = albumService.findAll();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Album>findById(@PathVariable Long id){
        Optional<Album> albumOptional = albumService.findById(id);
        return new ResponseEntity<>(albumOptional.get(), HttpStatus.OK);
    }
}
