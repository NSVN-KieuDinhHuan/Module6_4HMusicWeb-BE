package com.codegym.g2m6appmusicbe.service.playlist;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.IGeneralService;

public interface IPlaylistService extends IGeneralService<Playlist> {
    Iterable<Playlist> findAllByUser(User user);
    void deleteByProcedure(Long playlist_id);
    Iterable<Playlist> findByUserId(Long user_id);
    Iterable<Playlist> findAllByNameContaining(String name);
    Iterable<Playlist> findAllByViewDesc();
    Iterable<Playlist> findAllByCreateDateDesc();
}
