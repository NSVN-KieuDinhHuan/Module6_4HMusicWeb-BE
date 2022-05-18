package com.codegym.g2m6appmusicbe.service.commentSong;

import com.codegym.g2m6appmusicbe.model.entity.CommentSong;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.service.IGeneralService;

public interface ICommentSongService extends IGeneralService<CommentSong> {
    Iterable<CommentSong> findAllBySong(Song song);
}
