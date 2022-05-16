package com.codegym.g2m6appmusicbe.service.tag;

import com.codegym.g2m6appmusicbe.model.entity.Tag;
import com.codegym.g2m6appmusicbe.repository.ITagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class TagService implements ITagService{
    @Autowired
    public ITagRepository tagRepository;
    @Override
    public Iterable<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void removeById(Long id) {
     tagRepository.deleteById(id);
    }
}
