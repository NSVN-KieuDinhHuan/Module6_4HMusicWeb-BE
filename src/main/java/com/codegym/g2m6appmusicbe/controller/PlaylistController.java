package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.playlist.IPlaylistService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/playlists")
public class PlaylistController {
    @Autowired
    private IPlaylistService playlistService;
    @Autowired
    private IUserService userService;
    @GetMapping("/user/{user_id}")
    public ResponseEntity<Iterable<Playlist>> getByUserId(@PathVariable Long user_id){
        Iterable<Playlist> playlists = playlistService.findByUserId(user_id);
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<Iterable<Playlist>> getAll(){
        Iterable<Playlist> playlists = playlistService.findAll();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getById(@PathVariable Long id){
        Optional<Playlist> playlist = playlistService.findById(id);
        if(!playlist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(playlist.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Playlist> create(@RequestBody Playlist playlist){
        return new ResponseEntity<>(playlistService.save(playlist), HttpStatus.CREATED);
    }

    //Xay dung ham creaty playlist by user
    @PostMapping("/user/{user_id}")
    public ResponseEntity<Playlist> createByUserId(@PathVariable Long user_id, @RequestBody Playlist playlist){
        Optional<User> userOptional = userService.findById(user_id);
        playlist.setUser(userOptional.get());
        return new ResponseEntity<>(playlistService.save(playlist), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> update(@PathVariable Long id, @RequestBody Playlist playlist){
        Optional<Playlist> optionalPlaylist = playlistService.findById(id);
        if(!optionalPlaylist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playlist.setId(id);
        return new ResponseEntity<>(playlistService.save(playlist), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Playlist> delete(@PathVariable Long id){
        Optional<Playlist> optionalPlaylist = playlistService.findById(id);
        if(!optionalPlaylist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playlistService.deleteByProcedure(id);
        return new ResponseEntity<>(optionalPlaylist.get(), HttpStatus.OK);
    }
}
