package com.codegym.g2m6appmusicbe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "like_song")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikeSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean value;
    @ManyToOne
    private Song song;
    @ManyToOne
    private User user;

    public LikeSong(boolean value, Song song, User user) {
        this.value = value;
        this.song = song;
        this.user = user;
    }
}