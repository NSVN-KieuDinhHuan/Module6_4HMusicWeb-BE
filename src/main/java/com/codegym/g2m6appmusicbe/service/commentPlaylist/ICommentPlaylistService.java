package com.codegym.g2m6appmusicbe.service.commentPlaylist;

import com.codegym.g2m6appmusicbe.model.entity.CommentPlaylist;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.service.IGeneralService;

public interface ICommentPlaylistService extends IGeneralService<CommentPlaylist> {
    Iterable<CommentPlaylist> findAllByPlaylist(Playlist playlist);
}
