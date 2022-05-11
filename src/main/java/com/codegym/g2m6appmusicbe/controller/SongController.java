package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private ISongService songService;


    @GetMapping()
    public ResponseEntity<Iterable<Song>> getAll(){
        Iterable<Song> songs = songService.findAll();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song>findById(@PathVariable Long id){
        Optional<Song> songOptional = songService.findById(id);
        return new ResponseEntity<>(songOptional.get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Song> save(@RequestBody Song song){
        Song song1 = new Song(song.getId(),song.getName(),song.getDescription(),song.getMp3File(),song.getImage(),song.getAuthor(), song.getArtists(),song.getUser(),song.getCategory(),song.getAlbum(),song.getPlaylists(),song.getTag());

        return new ResponseEntity<>(songService.save(song1),HttpStatus.CREATED);
    }
}
