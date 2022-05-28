package com.codegym.g2m6appmusicbe.service.playlist;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.repository.ILikePlaylistRepository;
import com.codegym.g2m6appmusicbe.repository.IPlaylistRepository;
import com.codegym.g2m6appmusicbe.service.IGeneralService;
import com.codegym.g2m6appmusicbe.service.likePlaylist.ILikePlaylistService;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaylistService implements IPlaylistService {
    @Autowired
    private IPlaylistRepository playlistRepository;
    @Autowired
    private ILikePlaylistRepository likePlaylistRepository;
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
        List<Integer> like_numbers = findTopPlaylistLikeNumer();
        for (int i = 0; i < topLikePlaylists.size(); i++) {
            topLikePlaylists.get(i).setLikes(like_numbers.get(i));
        }
        return topLikePlaylists;
    }

    @Override
    public List<Integer> findTopPlaylistLikeNumer() {
        return likePlaylistRepository.findTopPlaylistLikeNumber();
    }


}
