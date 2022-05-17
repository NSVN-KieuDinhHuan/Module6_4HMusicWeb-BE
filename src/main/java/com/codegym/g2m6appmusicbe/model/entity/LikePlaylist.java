package com.codegym.g2m6appmusicbe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "like_playlist")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikePlaylist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean value;
    @ManyToOne
    private Playlist playlist;
    @ManyToOne
    private User user;

    public LikePlaylist(boolean value, Playlist playlist, User user) {
        this.value = value;
        this.playlist = playlist;
        this.user = user;
    }
}