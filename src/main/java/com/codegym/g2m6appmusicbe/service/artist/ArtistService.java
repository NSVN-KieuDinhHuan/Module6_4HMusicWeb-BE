package com.codegym.g2m6appmusicbe.service.artist;

import com.codegym.g2m6appmusicbe.model.entity.Artist;
import com.codegym.g2m6appmusicbe.repository.IArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtistService implements IArtistService{

    @Autowired
    IArtistRepository artistRepository;
    @Override
    public Iterable<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public Optional<Artist> findById(Long id) {
        return artistRepository.findById(id);
    }

    @Override
    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public void removeById(Long id) {

    }


}
