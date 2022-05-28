package com.codegym.g2m6appmusicbe.service.likeSong;

import com.codegym.g2m6appmusicbe.model.entity.LikeSong;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.IGeneralService;

import java.util.Optional;

public interface ILikeSongService extends IGeneralService<LikeSong> {
    Optional<LikeSong> findBySongAndUser(Song song, User user);
    Iterable<LikeSong> findAllBySong(Song song);
}
