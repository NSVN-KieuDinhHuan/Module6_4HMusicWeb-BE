package com.codegym.g2m6appmusicbe.service.likePlaylist;

import com.codegym.g2m6appmusicbe.model.entity.LikePlaylist;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.repository.ILikePlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikePlaylistService implements ILikePlaylistService{
    @Autowired
    private ILikePlaylistRepository likePlaylistRepository;
    @Override
    public Iterable<LikePlaylist> findAll() {
        return likePlaylistRepository.findAll();
    }

    @Override
    public Optional<LikePlaylist> findById(Long id) {
        return likePlaylistRepository.findById(id);
    }

    @Override
    public LikePlaylist save(LikePlaylist likePlaylist) {
        return likePlaylistRepository.save(likePlaylist);
    }

    @Override
    public void removeById(Long id) {
        likePlaylistRepository.deleteById(id);
    }

    @Override
    public Optional<LikePlaylist> findByPlaylistAndUser(Playlist playlist, User user) {
        return likePlaylistRepository.findByPlaylistAndUser(playlist, user);
    }
}
