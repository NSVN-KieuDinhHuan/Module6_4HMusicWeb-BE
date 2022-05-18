package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.entity.Artist;
import com.codegym.g2m6appmusicbe.service.artist.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("artists")
public class ArtistController {
    @Autowired
   private IArtistService artistService;

    @GetMapping
    public ResponseEntity<Iterable<Artist>> getAll(){
        Iterable<Artist> artists = artistService.findAll();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Artist>findById(@PathVariable Long id){
        Optional<Artist> artistOptional = artistService.findById(id);
        return new ResponseEntity<>(artistOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Artist> create(@RequestBody Artist artist){
        Artist artist1 = new Artist(artist.getId(),artist.getName(),artist.isGender(),artist.getDateOfBirth(),artist.getStory(),artist.getCategory(),artist.getBand(),artist.getMoreInfo(),0,0);
        return new ResponseEntity<>(artistService.save(artist1), HttpStatus.CREATED);
    }
}
