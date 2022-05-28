package com.codegym.g2m6appmusicbe.service.commentPlaylist;

import com.codegym.g2m6appmusicbe.model.entity.CommentPlaylist;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.repository.ICommentPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentPlaylistService implements ICommentPlaylistService{
    @Autowired
    private ICommentPlaylistRepository commentPlaylistRepository;
    @Override
    public Iterable<CommentPlaylist> findAll() {
        return this.commentPlaylistRepository.findAll();
    }

    @Override
    public Optional<CommentPlaylist> findById(Long id) {
        return this.commentPlaylistRepository.findById(id);
    }

    @Override
    public CommentPlaylist save(CommentPlaylist commentPlaylist) {
        return this.commentPlaylistRepository.save(commentPlaylist);
    }

    @Override
    public void removeById(Long id) {
        this.commentPlaylistRepository.deleteById(id);
    }

    @Override
    public Iterable<CommentPlaylist> findAllByPlaylist(Playlist playlist) {
        return this.commentPlaylistRepository.findAllByPlaylist(playlist);
    }
}
