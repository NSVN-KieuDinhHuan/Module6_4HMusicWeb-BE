package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Artist;
import com.codegym.g2m6appmusicbe.model.entity.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface IArtistRepository extends PagingAndSortingRepository<Artist, Long> {

}
