package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISongRepository extends PagingAndSortingRepository<Song, Long> {
    Iterable<Song> findByNameContaining(String name);
    @Query(value = "select * from songs where user_id = ?1", nativeQuery = true)
    Iterable<Song> findCreatedSongByUserId(Long user_id);
}
