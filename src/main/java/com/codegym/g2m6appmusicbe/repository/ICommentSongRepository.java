package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.CommentPlaylist;
import com.codegym.g2m6appmusicbe.model.entity.CommentSong;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentSongRepository extends JpaRepository<CommentSong, Long> {
    Iterable<CommentSong> findAllBySong(Song song);
}
