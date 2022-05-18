package com.codegym.g2m6appmusicbe.service.song;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.service.IGeneralService;

import java.util.List;

public interface ISongService extends IGeneralService<Song> {
    Iterable<Song> findSongByNameContaining(String name);
    Iterable<Song> findCreatedSongByUserId(Long user_id);
    Iterable<Song> findAllByViewDesc();
    Song findTopViewsSong();
    List<Song> findTopLikeSong();
    List<Long> findTopSongLikeNumer();
}
