package com.codegym.g2m6appmusicbe.service.likeSong;

import com.codegym.g2m6appmusicbe.model.entity.LikeSong;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.repository.ILikeSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeSongService implements ILikeSongService{
    @Autowired
    private ILikeSongRepository likeSongRepository;
    @Override
    public Iterable<LikeSong> findAll() {
        return likeSongRepository.findAll();
    }

    @Override
    public Optional<LikeSong> findById(Long id) {
        return likeSongRepository.findById(id);
    }

    @Override
    public LikeSong save(LikeSong likeSong) {
        return likeSongRepository.save(likeSong);
    }

    @Override
    public void removeById(Long id) {
        likeSongRepository.deleteById(id);
    }

    @Override
    public Optional<LikeSong> findBySongAndUser(Song song, User user) {
        return likeSongRepository.findBySongAndUser(song, user);
    }

    @Override
    public Iterable<LikeSong> findAllBySong(Song song) {
        return likeSongRepository.findAllBySong(song);
    }
}
