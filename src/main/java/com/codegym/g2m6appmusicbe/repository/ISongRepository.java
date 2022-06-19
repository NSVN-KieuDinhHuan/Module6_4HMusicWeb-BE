package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Artist;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


public interface ISongRepository extends PagingAndSortingRepository<Song, Long> {

    List<Song> findByNameContaining(String name);
    @Query(value = "select * from songs where user_id = ?1", nativeQuery = true)
    Iterable<Song> findCreatedSongByUserId(Long user_id);
    @Query(value = "select * from songs order by views desc limit 9", nativeQuery = true)
    Iterable<Song> findAllByViewDesc();
    @Query(value = "select * from songs order by views desc limit 1", nativeQuery = true)
    Song findTopViewsSong();
    @Query(value = "select * from songs where user_id = ?1 and id=?2 ", nativeQuery = true)
    Optional<Song> findSongByIdaAndUserId(Long user_id, Long id);

    @Query(value = "select * from songs where artist_id = ?1", nativeQuery = true)
    Iterable<Song> findArtistByIdAndSongId(Long artist_id);
}
