package com.codegym.g2m6appmusicbe.service.playlist;

import com.codegym.g2m6appmusicbe.model.dto.UserPrincipal;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.repository.ILikePlaylistRepository;
import com.codegym.g2m6appmusicbe.repository.IPlaylistRepository;
import com.codegym.g2m6appmusicbe.service.IGeneralService;
import com.codegym.g2m6appmusicbe.service.likePlaylist.ILikePlaylistService;
import com.codegym.g2m6appmusicbe.service.song.ISongService;
import com.codegym.g2m6appmusicbe.service.user.UserService;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaylistService implements IPlaylistService {
    @Autowired
    private UserService userService;
    @Autowired
    private IPlaylistRepository playlistRepository;
    @Autowired
    private ILikePlaylistRepository likePlaylistRepository;
    @Autowired
    private ISongService songService;
    @Override
    public Iterable<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return playlistRepository.findById(id);
    }

    @Override
    public Playlist save(Playlist playlist) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long user_id = userPrincipal.getId();
        Optional<User> userOptional = userService.findById(user_id);
        playlist.setUser(userOptional.orElse(new User()));
        playlist.setViews(0L);
        playlist.setCreateDate(new Date());
        playlist.setLikes(0);
        playlist.setComments(0);
        return playlistRepository.save(playlist);
    }

    @Override
    public void removeById(Long id) {
        playlistRepository.deleteById(id);
    }

    @Override
    public Iterable<Playlist> findAllByUser(User user) {
        return playlistRepository.findAllByUser(user);
    }

    @Override
    public void deleteByProcedure(Long playlist_id) {
        playlistRepository.deleteByProcedure(playlist_id);
    }

    @Override
    public Iterable<Playlist> findByUserId(Long user_id) {
        return playlistRepository.findByUserId(user_id);
    }

    @Override
    public Iterable<Playlist> findAllByNameContaining(String name) {
        return playlistRepository.findAllByNameContaining(name);
    }

    @Override
    public Iterable<Playlist> findAllByViewDesc() {
        return playlistRepository.findAllByViewDesc();
    }

    @Override
    public Iterable<Playlist> findAllByCreateDateDesc() {
        return playlistRepository.findAllByCreateDateDesc();
    }

    @Override
    public List<Playlist> findTopLikePlaylist() {
        List<Long> topLikePlaylistIds = likePlaylistRepository.findTopLikePlaylistId();
        List<Playlist> topLikePlaylists = new ArrayList<>();
        for (Long id : topLikePlaylistIds) {
            Playlist playlist = playlistRepository.findById(id).get();
            topLikePlaylists.add(playlist);
        }
        return topLikePlaylists;
    }

    @Override
    public List<Long> findTopPlaylistLikeNumer() {
        return likePlaylistRepository.findTopPlaylistLikeNumber();
    }

    @Override
    public void addSong(Long songId, Long playlistId){
        Optional<Song> song = songService.findById(songId);
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        List<Song> songs = playlist.orElse(new Playlist()).getSongs();
        songs.add(song.orElse(new Song()));
        playlist.orElse(new Playlist()).setSongs(songs);
    }

    @Override
    public void removeSong(Long songId, Long playlistId) {
        Optional<Song> song = songService.findById(songId);
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        List<Song> songs = playlist.get().getSongs();
        for (int i = 0; i < songs.size(); i++) {
            if(songId == songs.get(i).getId()){
                songs.remove(i);
                break;
            }
        }
        playlist.get().setSongs(songs);
    }


}
