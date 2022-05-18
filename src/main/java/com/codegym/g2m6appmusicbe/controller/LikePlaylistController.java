package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.entity.LikePlaylist;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.likePlaylist.ILikePlaylistService;
import com.codegym.g2m6appmusicbe.service.playlist.IPlaylistService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/likePlaylist")
public class LikePlaylistController {
    @Autowired
    private ILikePlaylistService likePlaylistService;
    @Autowired
    private IPlaylistService playlistService;
    @Autowired
    private IUserService userService;
    @PostMapping("/addLike/playlist/{playlistId}/user/{userId}")
    public ResponseEntity<LikePlaylist> addLikeToPlaylist(@PathVariable Long playlistId, @PathVariable Long userId){
        Optional<Playlist> playlistOptional = playlistService.findById(playlistId);
        if(!playlistOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<LikePlaylist> likePlaylistOptional = likePlaylistService.findByPlaylistAndUser(playlistOptional.get(), userOptional.get());
        if(likePlaylistOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LikePlaylist likePlaylist = new LikePlaylist(true,playlistOptional.get(), userOptional.get());
        int oldLikes = playlistOptional.get().getLikes();
        playlistOptional.get().setLikes(oldLikes + 1);
        playlistService.save(playlistOptional.get());
        return new ResponseEntity<>(likePlaylistService.save(likePlaylist), HttpStatus.OK);
    }

    // Lấy ra likePlaylist theo playlistId và UserId, check xem value là true hay false
    @PutMapping("/changeLikeStatus/playlist/{playlistId}/user/{userId}")
    public ResponseEntity<LikePlaylist> removeLikePlaylist(@PathVariable Long playlistId, @PathVariable Long userId){
        Optional<Playlist> playlistOptional = playlistService.findById(playlistId);
        if(!playlistOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<LikePlaylist> optionalLikePlaylist = likePlaylistService.findByPlaylistAndUser(playlistOptional.get(), userOptional.get());
        if(!optionalLikePlaylist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(optionalLikePlaylist.get().isValue()){
            optionalLikePlaylist.get().setValue(false);
            int oldLikes = playlistOptional.get().getLikes();
            playlistOptional.get().setLikes(oldLikes - 1);
        } else {
            optionalLikePlaylist.get().setValue(true);
            int oldLikes = playlistOptional.get().getLikes();
            playlistOptional.get().setLikes(oldLikes + 1);
        }
        playlistService.save(playlistOptional.get());
        return new ResponseEntity<>(likePlaylistService.save(optionalLikePlaylist.get()), HttpStatus.OK);
    }
}
