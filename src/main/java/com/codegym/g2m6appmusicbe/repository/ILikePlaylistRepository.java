package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.LikePlaylist;
import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import com.codegym.g2m6appmusicbe.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILikePlaylistRepository extends JpaRepository<LikePlaylist, Long> {
    Optional<LikePlaylist> findByPlaylistAndUser(Playlist playlist, User user);
    Iterable<LikePlaylist> findAllByPlaylist(Playlist playlist);

    @Query(value = "select playlist_id from (select count(user_id) as like_number, playlist_id from like_playlist\n" +
            "group by playlist_id order by like_number desc limit 5) as like_number_playlist", nativeQuery = true)
    List<Long> findTopLikePlaylistId();

    @Query(value = "select like_number from (select count(user_id) as like_number, playlist_id from like_playlist\n" +
            "group by playlist_id order by like_number desc limit 5) as like_number_playlist", nativeQuery = true)
    List<Long> findTopPlaylistLikeNumber();


}
