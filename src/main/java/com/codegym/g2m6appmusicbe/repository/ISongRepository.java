package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ISongRepository extends PagingAndSortingRepository<Song, Long> {
    Iterable<Song> findByNameContaining(String name);
    @Query(value = "select * from songs where user_id = ?1", nativeQuery = true)
    Iterable<Song> findCreatedSongByUserId(Long user_id);
    @Query(value = "select * from songs where user_id = ?1 and id=?2 ", nativeQuery = true)
    Optional<Song> findSongByIdaAndUserId(Long user_id, Long id);
}
