package com.codegym.g2m6appmusicbe.controller;

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
import org.springframework.web.bind.annotation.*;

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
        playlist.setViews(0L);
        playlist.setCreateDate(new Date());
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
        if(!optionalPlaylist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playlist.setId(id);
        playlist.setSongs(optionalPlaylist.get().getSongs());
        playlist.setLastUpdate(optionalPlaylist.get().getLastUpdate());
        playlist.setUser(optionalPlaylist.get().getUser());
//        playlist.setCategory(optionalPlaylist.get().getCategory());
        playlist.setCreateDate(optionalPlaylist.get().getCreateDate());
//        playlist.setDescription(optionalPlaylist.get().getDescription());
        playlist.setViews(optionalPlaylist.get().getViews());
        return new ResponseEntity<>(playlistService.save(playlist), HttpStatus.OK);
    }

    @PostMapping("/addSong")
    public ResponseEntity<Playlist> addSongToPlaylist(@RequestParam(name = "songId") Long songId, @RequestParam(name = "playlistId") Long playlistId){
        Optional<Song> song = songService.findById(songId);
        Optional<Playlist> playlist = playlistService.findById(playlistId);
        if(!song.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!playlist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Song> songs = playlist.get().getSongs();
        songs.add(song.get());
        playlist.get().setSongs(songs);
        return new ResponseEntity<>(playlistService.save(playlist.get()), HttpStatus.OK);
    }

    @PostMapping("removeSong")
    public ResponseEntity<Playlist> removeSong(@RequestParam(name = "songId") Long songId, @RequestParam(name = "playlistId") Long playlistId){
        Optional<Song> song = songService.findById(songId);
        Optional<Playlist> playlist = playlistService.findById(playlistId);
        if(!song.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!playlist.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Song> songs = playlist.get().getSongs();
        for (int i = 0; i < songs.size(); i++) {
            if(songId == songs.get(i).getId()){
                songs.remove(i);
                break;
            }
        }
        playlist.get().setSongs(songs);
        return new ResponseEntity<>(playlistService.save(playlist.get()), HttpStatus.OK);
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
