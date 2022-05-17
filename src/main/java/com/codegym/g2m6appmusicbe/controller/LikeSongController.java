package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.entity.*;
import com.codegym.g2m6appmusicbe.service.likeSong.ILikeSongService;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/likeSong")
public class LikeSongController {
    @Autowired
    private ISongService songService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ILikeSongService likeSongService;
    @PostMapping("/addLike/song/{songId}/user/{userId}")
    public ResponseEntity<LikeSong> addLikeSong(@PathVariable Long songId, @PathVariable Long userId){
        Optional<Song> songOptional = songService.findById(songId);
        if(!songOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<LikeSong> likeSongOptional = likeSongService.findBySongAndUser(songOptional.get(), userOptional.get());
        if(likeSongOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LikeSong likeSong = new LikeSong(true,songOptional.get(), userOptional.get());
        int oldLikes = songOptional.get().getLikes();
        songOptional.get().setLikes(oldLikes + 1);
        songService.save(songOptional.get());
        return new ResponseEntity<>(likeSongService.save(likeSong), HttpStatus.OK);
    }

    // Lấy ra likeSong theo songId và UserId, check xem value là true hay false để đổi trạng thái
    @PutMapping("/changeLikeStatus/song/{songId}/user/{userId}")
    public ResponseEntity<LikeSong> removeLikeSong(@PathVariable Long songId, @PathVariable Long userId){
        Optional<Song> songOptional = songService.findById(songId);
        if(!songOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<LikeSong> optionalLikeSong = likeSongService.findBySongAndUser(songOptional.get(), userOptional.get());
        if(!optionalLikeSong.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(optionalLikeSong.get().isValue()){
            optionalLikeSong.get().setValue(false);
            int oldLikes = songOptional.get().getLikes();
            songOptional.get().setLikes(oldLikes - 1);
        } else {
            optionalLikeSong.get().setValue(true);
            int oldLikes = songOptional.get().getLikes();
            songOptional.get().setLikes(oldLikes + 1);
        }
        songService.save(songOptional.get());
        return new ResponseEntity<>(likeSongService.save(optionalLikeSong.get()), HttpStatus.OK);
    }
}
