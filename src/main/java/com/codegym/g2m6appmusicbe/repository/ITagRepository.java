package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ITagRepository extends PagingAndSortingRepository<Tag,Long> {
}
