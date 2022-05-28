package com.codegym.g2m6appmusicbe.service.category;

import com.codegym.g2m6appmusicbe.model.entity.Category;
import com.codegym.g2m6appmusicbe.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CategoryService implements ICategoryService{
    @Autowired
    public ICategoryRepository categoryRepository;
    @Override
    public Iterable findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }
}
