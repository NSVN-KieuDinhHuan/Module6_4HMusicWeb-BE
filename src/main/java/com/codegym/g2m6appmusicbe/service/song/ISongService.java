package com.codegym.g2m6appmusicbe.service.song;

import com.codegym.g2m6appmusicbe.model.dto.SongForm;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.service.IGeneralService;

import java.util.Optional;

import java.util.List;

public interface ISongService extends IGeneralService<Song> {
    Iterable<Song> findSongByNameContaining(String name);
    Iterable<Song> findCreatedSongByUserId();
    Optional<Song> findSongByIdaAndUserId(Long user_id, Long id);

    Iterable<Song> findArtistByIdAndSongId(Long artist_id);
    Iterable<Song> findAllByViewDesc();
    Song findTopViewsSong();
    List<Song> findTopLikeSong();
    List<Long> findTopSongLikeNumer();
    Song saveSong(SongForm songForm);
    Song viewSong(Long Songid);
    Song updateSong(Long id,SongForm songForm);

}
