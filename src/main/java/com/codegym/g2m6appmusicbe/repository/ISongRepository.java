package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Song;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISongRepository extends PagingAndSortingRepository<Song, Long> {
Iterable<Song> findByNameContaining(String name);
}
