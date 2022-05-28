package com.codegym.g2m6appmusicbe.service.likePlaylist;

import com.codegym.g2m6appmusicbe.model.entity.LikePlaylist;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.IGeneralService;

import java.util.Optional;

public interface ILikePlaylistService extends IGeneralService<LikePlaylist> {
    Optional<LikePlaylist> findByPlaylistAndUser(Playlist playlist, User user);
    Iterable<LikePlaylist> findAllByPlaylist(Playlist playlist);
}
