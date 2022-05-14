package com.codegym.g2m6appmusicbe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comments_song")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    private Song song;
    @ManyToOne
    private User user;
}
