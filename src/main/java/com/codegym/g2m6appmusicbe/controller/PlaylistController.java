package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.dto.UserPrincipal;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.playlist.IPlaylistService;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/playlists")
public class PlaylistController {
    @Autowired
    private IPlaylistService playlistService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISongService songService;
    @GetMapping("/user")
    public ResponseEntity<Iterable<Playlist>> getByUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long user_id = userPrincipal.getId();
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
        return new ResponseEntity<>(playlist.orElse(new Playlist()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Playlist> create(@RequestBody Playlist playlist){
        return new ResponseEntity<>(playlistService.save(playlist), HttpStatus.CREATED);
    }

    //Xay dung ham create playlist by user
    @PostMapping("/user")
    public ResponseEntity<Playlist> createByUserId(@RequestBody Playlist playlist){
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

    @PutMapping("/editPlaylist/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlist){
        Optional<Playlist> optionalPlaylist = playlistService.findById(id);
        playlist.setId(id);
        playlist.setSongs(optionalPlaylist.get().getSongs());
        playlist.setLastUpdate(optionalPlaylist.get().getLastUpdate());
        playlist.setUser(optionalPlaylist.get().getUser());
        playlist.setCreateDate(optionalPlaylist.get().getCreateDate());
        playlist.setViews(optionalPlaylist.get().getViews());
        return new ResponseEntity<>(playlistService.save(playlist), HttpStatus.OK);
    }

    @PostMapping("/addSong")
    public ResponseEntity<Playlist> addSongToPlaylist(@RequestParam(name = "songId") Long songId, @RequestParam(name = "playlistId") Long playlistId){
        Optional<Playlist> playlist = playlistService.findById(playlistId);
        if(!playlist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playlistService.addSong(songId, playlistId);
        return new ResponseEntity<>(playlistService.save(playlist.orElse(new Playlist())), HttpStatus.OK);
    }

    @PostMapping("removeSong")
    public ResponseEntity<Playlist> removeSong(@RequestParam(name = "songId") Long songId, @RequestParam(name = "playlistId") Long playlistId){
        Optional<Playlist> playlist = playlistService.findById(playlistId);
        if(!playlist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playlistService.removeSong(songId,playlistId);
        return new ResponseEntity<>(playlistService.save(playlist.orElse(new Playlist())), HttpStatus.OK);
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

    @GetMapping("/search/{name}")
    public ResponseEntity<Iterable<Playlist>> findAllByName(@PathVariable String name){
        Iterable<Playlist> playlists = playlistService.findAllByNameContaining(name);
        if(playlists == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/getMostViews")
    public ResponseEntity<Iterable<Playlist>> findMostViewsPlaylist(){
        Iterable<Playlist> playlists = playlistService.findAllByViewDesc();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/getNewest")
    public ResponseEntity<Iterable<Playlist>> findNewestPlaylists(){
        Iterable<Playlist> playlists = playlistService.findAllByCreateDateDesc();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/getTopLikePlaylist")
    public ResponseEntity<Iterable<Playlist>> getTopLikePlaylists(){
        List<Playlist> playlists = playlistService.findTopLikePlaylist();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/getTopLikeNumber")
    public ResponseEntity<Iterable<Long>> getTopLikeNumber(){
        List<Long> likeNumbers = playlistService.findTopPlaylistLikeNumer();
        return new ResponseEntity<>(likeNumbers, HttpStatus.OK);
    }

}
