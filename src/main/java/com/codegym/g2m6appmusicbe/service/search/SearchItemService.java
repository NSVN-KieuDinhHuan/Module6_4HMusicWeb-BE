package com.codegym.g2m6appmusicbe.service.search;

import com.codegym.g2m6appmusicbe.model.dto.SearchItem;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.repository.IPlaylistRepository;
import com.codegym.g2m6appmusicbe.repository.ISongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchItemService {
    @Autowired
    private ISongRepository songRepository;
    @Autowired
    private IPlaylistRepository playlistRepository;

    public SearchItem getSearchItem(String q){
        List<Song> songs = songRepository.findAllByNameContaining(q);
        List<Playlist> playlists = playlistRepository.findAllByNameContaining(q);
        return new SearchItem(songs, playlists);
    }
}
