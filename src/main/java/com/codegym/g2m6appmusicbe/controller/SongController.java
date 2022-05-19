package com.codegym.g2m6appmusicbe.controller;

import com.codegym.g2m6appmusicbe.model.dto.SongForm;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/user/{user_id}")
    public ResponseEntity<Song> save(@ModelAttribute SongForm songForm ,@PathVariable Long user_id){
        Optional<User> user=userService.findById(user_id);
        MultipartFile song=songForm.getMp3File();
        MultipartFile image=songForm.getImage();
        long currentTime = System.currentTimeMillis(); //Xử lý lấy thời gian hiện tại
        String imageName="";
        String songName="";
        if (image.getSize()!=0){
            imageName=currentTime+songForm.getImage().getOriginalFilename();
            try {
                FileCopyUtils.copy(songForm.getImage().getBytes(), new File(uploadPath + imageName));
            }catch (IOException e){
            e.printStackTrace();
            }
        }
        if (song.getSize()!=0){
            songName=currentTime+songForm.getMp3File().getOriginalFilename();
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

           @PostMapping ("/user/{user_id}/{id}")
    public ResponseEntity<Song> update(@PathVariable Long user_id,@PathVariable Long id,@ModelAttribute SongForm songForm){
        Optional<User> user=userService.findById(user_id);
        Optional<Song> oldsong=songService.findById(id);
        long currentTime = System.currentTimeMillis(); //Xử lý lấy thời gian hiện tại

        if(!oldsong.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MultipartFile song=songForm.getMp3File();
        MultipartFile image=songForm.getImage();
        String imageName="";
        String songName="";
        if (image==null) {
            imageName=oldsong.get().getImage();
        }else {
            imageName = currentTime+songForm.getImage().getOriginalFilename();
            try {
                FileCopyUtils.copy(songForm.getImage().getBytes(), new File(uploadPath + imageName));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (song==null) {
            songName=oldsong.get().getMp3File();
        }else {
            songName = currentTime+songForm.getMp3File().getOriginalFilename();
            try {
                FileCopyUtils.copy(songForm.getMp3File().getBytes(), new File(uploadPath + songName));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Song song1 = new Song(id,songForm.getName(),songForm.getDescription(),songName,imageName,songForm.getAuthor(), user.get(), songForm.getCategory(),songForm.getAlbum(),songForm.getTag(), 0, songForm.getArtist(),0,0);

        return new ResponseEntity<>(songService.save(song1),HttpStatus.OK);
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
