package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.dto.SongForm;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
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
    @Autowired
    private IUserService userService;
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
    @PostMapping("/user/{user_id}")
    public ResponseEntity<Song> save(@ModelAttribute SongForm songForm ,@PathVariable Long user_id){
        Optional<User> user=userService.findById(user_id);
        MultipartFile song=songForm.getMp3File();
        MultipartFile image=songForm.getImage();
        String imageName="";
        String songName="";
        if (image.getSize()!=0){
            imageName=songForm.getImage().getOriginalFilename();
            try {
                FileCopyUtils.copy(songForm.getImage().getBytes(), new File(uploadPath + imageName));
            }catch (IOException e){
            e.printStackTrace();
            }
        }
        if (song.getSize()!=0){
            songName=songForm.getMp3File().getOriginalFilename();
            try {
                FileCopyUtils.copy(songForm.getMp3File().getBytes(), new File(uploadPath + songName));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Song song1 = new Song(songForm.getId(),songForm.getName(),songForm.getDescription(),songName,imageName,songForm.getAuthor(), user.get(), songForm.getCategory(),songForm.getAlbum(),songForm.getTag(), 0, songForm.getArtist(),0,0);
        return new ResponseEntity<>(songService.save(song1),HttpStatus.CREATED);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<Iterable<Song>> getAllCreatedSongByUser(@PathVariable Long user_id){
        Iterable<Song> songs = songService.findCreatedSongByUserId(user_id);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
}
