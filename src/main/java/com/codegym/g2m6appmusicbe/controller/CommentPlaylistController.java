package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.entity.CommentPlaylist;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.commentPlaylist.ICommentPlaylistService;
import com.codegym.g2m6appmusicbe.service.playlist.IPlaylistService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("commentPlaylist")
public class CommentPlaylistController {
    @Autowired
    private ICommentPlaylistService commentPlaylistService;
    @Autowired
    private IPlaylistService playlistService;
    @Autowired
    private IUserService userService;
    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<Iterable<CommentPlaylist>> findAllByPlaylist(@PathVariable Long playlistId){
        Optional<Playlist> playlistOptional = playlistService.findById(playlistId);
        if(!playlistOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentPlaylistService.findAllByPlaylist(playlistOptional.get()), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CommentPlaylist> createNewComment(@RequestParam (name = "playlistId") Long playlistId,
                                                            @RequestParam (name = "userId") Long userId, @RequestBody CommentPlaylist commentPlaylist){
        Optional<Playlist> playlistOptional = playlistService.findById(playlistId);
        if(!playlistOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentPlaylist.setPlaylist(playlistOptional.get());
        commentPlaylist.setUser(userOptional.get());
        int oldComments = playlistOptional.get().getComments();
        playlistOptional.get().setComments(oldComments + 1);
//        playlistService.save(playlistOptional.get());
        return new ResponseEntity<>(commentPlaylistService.save(commentPlaylist), HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentPlaylist> updateComment(@PathVariable Long commentId, @RequestBody CommentPlaylist commentPlaylist){
       Optional<CommentPlaylist> commentPlaylistOptional = commentPlaylistService.findById(commentId);
       if(!commentPlaylistOptional.isPresent()){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       commentPlaylist.setPlaylist(commentPlaylistOptional.get().getPlaylist());
       commentPlaylist.setUser(commentPlaylistOptional.get().getUser());
       commentPlaylist.setId(commentId);
        return new ResponseEntity<>(commentPlaylistService.save(commentPlaylistOptional.get()), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentPlaylist> deleteComment(@PathVariable Long commentId){
        Optional<CommentPlaylist> commentPlaylistOptional = commentPlaylistService.findById(commentId);
        if(!commentPlaylistOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentPlaylistService.removeById(commentId);
        int oldComments = commentPlaylistOptional.get().getPlaylist().getComments();
        commentPlaylistOptional.get().getPlaylist().setComments(oldComments - 1);
        playlistService.save(commentPlaylistOptional.get().getPlaylist());
        return new ResponseEntity<>(commentPlaylistOptional.get(), HttpStatus.OK);
    }
}
