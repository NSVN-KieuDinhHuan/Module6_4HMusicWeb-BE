package com.codegym.g2m6appmusicbe.service.song;

import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.repository.ISongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SongService implements ISongService{
    @Autowired
    private ISongRepository songRepository;

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
    public Iterable<Song> findCreatedSongByUserId(Long user_id) {
        return songRepository.findCreatedSongByUserId(user_id);
    }

    @Override
    public Optional<Song> findSongByIdaAndUserId(Long user_id, Long id) {
        return songRepository.findSongByIdaAndUserId(user_id,id);
    }

    @Override
    public Iterable<Song> findArtistByIdAndSongId(Long artist_id) {
        return songRepository.findArtistByIdAndSongId(artist_id);
    }

}
