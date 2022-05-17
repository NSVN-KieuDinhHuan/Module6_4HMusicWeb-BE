package com.codegym.g2m6appmusicbe.service.commentSong;

import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.repository.ICommentSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentSongService implements ICommentSongService {
    @Autowired
    private ICommentSongRepository commentSongRepository;
    @Override
    public Iterable<com.codegym.g2m6appmusicbe.model.entity.CommentSong> findAll() {
        return commentSongRepository.findAll();
    }

    @Override
    public Optional<com.codegym.g2m6appmusicbe.model.entity.CommentSong> findById(Long id) {
        return commentSongRepository.findById(id);
    }

    @Override
    public com.codegym.g2m6appmusicbe.model.entity.CommentSong save(com.codegym.g2m6appmusicbe.model.entity.CommentSong commentSong) {
        return commentSongRepository.save(commentSong);
    }

    @Override
    public void removeById(Long id){
        commentSongRepository.deleteById(id);
    }

    @Override
    public Iterable<com.codegym.g2m6appmusicbe.model.entity.CommentSong> findAllBySong(Song song) {
        return commentSongRepository.findAllBySong(song);
    }
}
