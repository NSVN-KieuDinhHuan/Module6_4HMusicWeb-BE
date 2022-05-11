package com.codegym.g2m6appmusicbe.repository;


import com.codegym.g2m6appmusicbe.model.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaylistRepository extends JpaRepository<Long, Playlist> {
}
