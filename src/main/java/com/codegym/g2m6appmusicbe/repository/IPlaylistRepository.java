package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Repository
//@Transactional
public interface IPlaylistRepository extends JpaRepository<Playlist, Long> {
    Iterable<Playlist> findAllByUser(User user);
    @Modifying
    @Query(value = "call delete_playlist1(?1)", nativeQuery = true)
    void deleteByProcedure(Long playlist_id);

    @Query(value = "select * from playlists where user_id = ?1", nativeQuery = true)
    Iterable<Playlist> findByUserId(Long user_id);

    List<Playlist> findAllByNameContaining(String name);

    @Query(value = "select * from playlists order by views desc limit 5",nativeQuery = true)
    Iterable<Playlist> findAllByViewDesc();

    @Query(value = "select * from playlists order by create_date desc limit 5", nativeQuery = true)
    Iterable<Playlist> findAllByCreateDateDesc();

}
