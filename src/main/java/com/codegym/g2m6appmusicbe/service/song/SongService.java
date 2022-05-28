package com.codegym.g2m6appmusicbe.service.song;

import com.codegym.g2m6appmusicbe.model.dto.SongForm;
import com.codegym.g2m6appmusicbe.model.dto.UserPrincipal;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.repository.ILikeSongRepository;
import com.codegym.g2m6appmusicbe.repository.ISongRepository;
import com.codegym.g2m6appmusicbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService implements ISongService{
    @Autowired
    private ISongRepository songRepository;
    @Autowired
    private ILikeSongRepository likeSongRepository;

    @Autowired
    private IUserService userService;
    @Value("${file-upload}")
    private String uploadPath;

    @Override
    public Iterable<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }

    @Override
    public void removeById(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    public Iterable<Song> findSongByNameContaining(String name) {
        return songRepository.findByNameContaining(name);
    }

    @Override
    public Iterable<Song> findCreatedSongByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long user_id = userPrincipal.getId();
        return songRepository.findCreatedSongByUserId(user_id);
    }

    @Override
    public Optional<Song> findSongByIdaAndUserId(Long user_id, Long id) {
        return songRepository.findSongByIdaAndUserId(user_id,id);
    }

    @Override
    public Iterable<Song> findAllByViewDesc() {
        return songRepository.findAllByViewDesc();
    }

    @Override
    public Song findTopViewsSong() {
        return songRepository.findTopViewsSong();
    }

    @Override
    public List<Song> findTopLikeSong() {
        List<Long> topLikeSongIds = likeSongRepository.findTopLikeSongId();
        List<Song> topLikeSongs = new ArrayList<>();
        for (Long id : topLikeSongIds) {
            Song song = songRepository.findById(id).get();
            topLikeSongs.add(song);
        }
        return topLikeSongs;
    }

    @Override
    public List<Long> findTopSongLikeNumer() {
        return likeSongRepository.findTopSongLikeNumber();
    }

    @Override
    public Song saveSong(SongForm songForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long user_id = userPrincipal.getId();
        Optional<User> user=userService.findById(user_id);
        MultipartFile song=songForm.getMp3File();
        MultipartFile image=songForm.getImage();
        long currentTime = System.currentTimeMillis(); //Xử lý lấy thời gian hiện tại
        String imageName="";
        String songName="";
        if (image.getSize()!=0){
            imageName=currentTime+songForm.getImage().getOriginalFilename();
        }
        if (song.getSize()!=0){
            songName=currentTime+songForm.getMp3File().getOriginalFilename();
        }

        try {
            FileCopyUtils.copy(songForm.getMp3File().getBytes(), new File(uploadPath + songName));
            FileCopyUtils.copy(songForm.getImage().getBytes(), new File(uploadPath + imageName));
        }catch (IOException e){
            e.printStackTrace();
        }
        Song song1 = new Song(songForm.getId(),songForm.getName(),songForm.getDescription(),songName,imageName,songForm.getAuthor(), user.get(), songForm.getCategory(),songForm.getAlbum(),songForm.getTag(), 0, songForm.getArtist(),0,0);
        return songRepository.save(song1);
    }

    @Override
    public Song viewSong(Long songid) {
        Optional<Song> optionalSong = songRepository.findById(songid);
        if(!optionalSong.isPresent()){
            return null;
        }
        optionalSong.get().setViews(optionalSong.get().getViews()+1);
        return  songRepository.save(optionalSong.get());
    }

    @Override
    public Song updateSong(Long id, SongForm songForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long user_id = userPrincipal.getId();
        Optional<User> user=userService.findById(user_id);
        Optional<Song> oldsong=songRepository.findById(id);
        long currentTime = System.currentTimeMillis(); //Xử lý lấy thời gian hiện tại

        if(!oldsong.isPresent()) {
            return null;
        }
        MultipartFile song=songForm.getMp3File();
        MultipartFile image=songForm.getImage();
        String imageName="";
        String songName="";
        if (image==null) {
            imageName=oldsong.get().getImage();
        }else {
            imageName = currentTime+songForm.getImage().getOriginalFilename();

        }
        if (song==null) {
            songName=oldsong.get().getMp3File();
        }else {
            songName = currentTime+songForm.getMp3File().getOriginalFilename();
        }
        try {
            FileCopyUtils.copy(songForm.getMp3File().getBytes(), new File(uploadPath + songName));
            FileCopyUtils.copy(songForm.getImage().getBytes(), new File(uploadPath + imageName));
        }catch (IOException e){
            e.printStackTrace();
        }

        Song song1 = new Song(id,songForm.getName(),songForm.getDescription(),songName,imageName,songForm.getAuthor(), user.get(), songForm.getCategory(),songForm.getAlbum(),songForm.getTag(), 0, songForm.getArtist(),0,0);
        return songRepository.save(song1);
    }

    @Override
    public Iterable<Song> findArtistByIdAndSongId(Long artist_id) {
        return songRepository.findArtistByIdAndSongId(artist_id);
    }

}
