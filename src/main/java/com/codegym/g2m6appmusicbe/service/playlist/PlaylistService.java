package com.codegym.g2m6appmusicbe.service.playlist;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.repository.IPlaylistRepository;
import com.codegym.g2m6appmusicbe.service.IGeneralService;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaylistService implements IPlaylistService {
    @Autowired
    private IPlaylistRepository playlistRepository;
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
}
