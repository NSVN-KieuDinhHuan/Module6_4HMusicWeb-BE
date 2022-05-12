package com.codegym.g2m6appmusicbe.service.abum;

import com.codegym.g2m6appmusicbe.model.entity.Album;
import com.codegym.g2m6appmusicbe.repository.IAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AlbumService implements IAlbumService{
    @Autowired
    IAlbumRepository albumRepository;
    @Override
    public Iterable<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    public Album save(Album album) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }
}
