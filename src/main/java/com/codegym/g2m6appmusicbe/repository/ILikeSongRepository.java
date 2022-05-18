package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.LikeSong;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import com.codegym.g2m6appmusicbe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILikeSongRepository extends JpaRepository<LikeSong, Long> {
    Optional<LikeSong> findBySongAndUser(Song song, User user);
    Iterable<LikeSong> findAllBySong(Song song);

    @Query(value = "select song_id from (select count(user_id) as like_number, song_id from like_song\n" +
            "group by song_id order by like_number desc limit 5) as like_number_song", nativeQuery = true)
    List<Long> findTopLikeSongId();

    @Query(value = "select like_number from (select count(user_id) as like_number, song_id from like_song group by song_id order by like_number desc limit 5) as like_number_song", nativeQuery = true)
    List<Long> findTopSongLikeNumber();
}
