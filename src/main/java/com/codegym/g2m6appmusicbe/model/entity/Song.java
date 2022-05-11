package com.codegym.g2m6appmusicbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "songs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String mp3File;
    private String image;
    private String author;
    @ManyToMany
    private List<Artist> artists;
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Album album;
    @ManyToOne
    private Playlist playlist;
    @ManyToOne
    private Tag tag;
}
