package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Artist;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IArtistRepository extends PagingAndSortingRepository<Artist, Long> {
}
