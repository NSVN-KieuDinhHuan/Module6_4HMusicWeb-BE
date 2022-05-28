package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.dto.SongForm;
import com.codegym.g2m6appmusicbe.model.dto.UserPrincipal;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
    @PostMapping("/user")
    public ResponseEntity<Song> save(@ModelAttribute SongForm songForm){
        return new ResponseEntity<>(songService.saveSong(songForm),HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<Iterable<Song>> getAllCreatedSongByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long user_id = userPrincipal.getId();
        Iterable<Song> songs = songService.findCreatedSongByUserId(user_id);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/artist/{artist_id}")
    public ResponseEntity<Iterable<Song>> getAllSongByArtist(@PathVariable Long artist_id){
        Iterable<Song> songs = songService.findArtistByIdAndSongId(artist_id);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Song> delete(@PathVariable Long id){
        Optional<Song> optionalSong = songService.findById(id);
        if(!optionalSong.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        songService.removeById(id);
        return new ResponseEntity<>(optionalSong.get(), HttpStatus.OK);
    }

       @PostMapping("views/{songid}")
       public ResponseEntity<Song> update(@PathVariable Long songid) {
           Optional<Song> optionalSong = songService.findById(songid);
           if(!optionalSong.isPresent()){
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }

           optionalSong.get().setViews(optionalSong.get().getViews()+1);
           return new ResponseEntity<>(songService.save(optionalSong.get()),HttpStatus.OK);
       }

       @PostMapping ("/user/{id}")
       public ResponseEntity<Song> update(@PathVariable Long id,@ModelAttribute SongForm songForm){
        return new ResponseEntity<>(songService.updateSong(id,songForm),HttpStatus.OK);
    }

    @GetMapping("/getMostViewSongs")
    public ResponseEntity<Iterable<Song>> getAllByViewDesc(){
        Iterable<Song> songs = songService.findAllByViewDesc();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    @GetMapping("/getTopViewSong")
    public ResponseEntity<Song> getTopViewSong(){
        Song song = songService.findTopViewsSong();
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/getTopLikeSong")
    public ResponseEntity<Iterable<Song>> getTopLikeSongs(){
        List<Song> songs = songService.findTopLikeSong();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/getTopLikeNumber")
    public ResponseEntity<Iterable<Long>> getTopLikeNumber(){
        List<Long> likeNumbers = songService.findTopSongLikeNumer();
        return new ResponseEntity<>(likeNumbers, HttpStatus.OK);
    }
}
