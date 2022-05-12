package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.dto.SongForm;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private ISongService songService;

    @Value("${file-upload}")
    private String uploadPath;

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
    public ResponseEntity<Song> save(@ModelAttribute SongForm songForm) {
        MultipartFile mp3File = songForm.getMp3File();
        MultipartFile image = songForm.getImage();
        String fileImage = songForm.getImage().getOriginalFilename();
        String fileMp3 = songForm.getMp3File().getOriginalFilename();
        if (image.getSize() != 0) {
            try {
                FileCopyUtils.copy(songForm.getImage().getBytes(), new File(uploadPath + fileImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mp3File.getSize() != 0) {
            try {
                FileCopyUtils.copy(songForm.getMp3File().getBytes(), new File(uploadPath + fileMp3));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Song song = new Song(songForm.getId(), songForm.getName(), songForm.getDescription(), fileMp3, fileImage, songForm.getAuthor(),songForm.getArtists(),songForm.getUser(),songForm.getCategory(),songForm.getAlbum(),songForm.getPlaylists(),songForm.getTag());
            return new ResponseEntity<>(songService.save(song), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Song> deleteSong(@PathVariable Long id) {
        Optional<Song> songOptional = songService.findById(id);
        if (!songOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        songService.removeById(id);
        return new ResponseEntity<>(songOptional.get(), HttpStatus.OK);
    }
}
