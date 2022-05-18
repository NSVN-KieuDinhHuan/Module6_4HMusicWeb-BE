package com.codegym.g2m6appmusicbe.service.song;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.repository.ILikeSongRepository;
import com.codegym.g2m6appmusicbe.repository.ISongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService implements ISongService{
    @Autowired
    private ISongRepository songRepository;
    @Autowired
    private ILikeSongRepository likeSongRepository;

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
}
