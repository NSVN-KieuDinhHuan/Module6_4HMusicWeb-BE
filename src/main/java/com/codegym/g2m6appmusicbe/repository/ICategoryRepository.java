package com.codegym.g2m6appmusicbe.repository;

import com.codegym.g2m6appmusicbe.model.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
