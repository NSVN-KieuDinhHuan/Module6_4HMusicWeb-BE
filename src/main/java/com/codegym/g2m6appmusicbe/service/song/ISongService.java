package com.codegym.g2m6appmusicbe.service.song;

import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.service.IGeneralService;

public interface ISongService extends IGeneralService<Song> {
    Iterable<Song> findSongByNameContaining(String name);
}
