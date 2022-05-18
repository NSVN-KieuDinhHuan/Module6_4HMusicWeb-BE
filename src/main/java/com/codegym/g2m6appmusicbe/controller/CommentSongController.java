package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.entity.CommentSong;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.commentSong.ICommentSongService;
import com.codegym.g2m6appmusicbe.service.commentSong.ICommentSongService;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("commentSong")
public class CommentSongController {
    @Autowired
    private ICommentSongService commentSongService;
    @Autowired
    private ISongService songService;
    @Autowired
    private IUserService userService;
    @GetMapping("/{songId}")
    public ResponseEntity<Iterable<CommentSong>> findAllBySong(@PathVariable Long songId){
        Optional<Song> songOptional = songService.findById(songId);
        if(!songOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentSongService.findAllBySong(songOptional.get()), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CommentSong> createNewComment(@RequestParam (name = "songId") Long songId,
                                                            @RequestParam (name = "userId") Long userId, @RequestBody CommentSong commentSong){
        Optional<Song> SongOptional = songService.findById(songId);
        if(!SongOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentSong.setSong(SongOptional.get());
        commentSong.setUser(userOptional.get());
        int oldComments = SongOptional.get().getComments();
        SongOptional.get().setComments(oldComments + 1);
//        SongService.save(SongOptional.get());
        return new ResponseEntity<>(commentSongService.save(commentSong), HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentSong> updateComment(@PathVariable Long commentId, @RequestBody CommentSong commentSong){
        Optional<CommentSong> commentSongOptional = commentSongService.findById(commentId);
        if(!commentSongOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentSong.setSong(commentSongOptional.get().getSong());
        commentSong.setUser(commentSongOptional.get().getUser());
        commentSong.setId(commentId);
        return new ResponseEntity<>(commentSongService.save(commentSongOptional.get()), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentSong> deleteComment(@PathVariable Long commentId){
        Optional<CommentSong> commentSongOptional = commentSongService.findById(commentId);
        if(!commentSongOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentSongService.removeById(commentId);
        int oldComments = commentSongOptional.get().getSong().getComments();
        commentSongOptional.get().getSong().setComments(oldComments - 1);
        songService.save(commentSongOptional.get().getSong());
        return new ResponseEntity<>(commentSongOptional.get(), HttpStatus.OK);
    }
}
