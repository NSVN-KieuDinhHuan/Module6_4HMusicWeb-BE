package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ISongRepository extends PagingAndSortingRepository<Song, Long> {
    Iterable<Song> findByNameContaining(String name);
    @Query(value = "select * from songs where user_id = ?1", nativeQuery = true)
    Iterable<Song> findCreatedSongByUserId(Long user_id);
    @Query(value = "select * from songs order by views desc limit 9", nativeQuery = true)
    Iterable<Song> findAllByViewDesc();
    @Query(value = "select * from songs order by views desc limit 1", nativeQuery = true)
    Song findTopViewsSong();

    List<Song> findAllByNameContaining(String q);

}
