package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Album;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IAlbumRepository extends PagingAndSortingRepository<Album, Long> {
}
