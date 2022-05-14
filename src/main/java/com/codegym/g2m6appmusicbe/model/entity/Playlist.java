package com.codegym.g2m6appmusicbe.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "playlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createDate;
    private Date lastUpdate;
    private Long views;
    // them list song
    @ManyToMany
    private List<Song> songs;
    @ManyToOne
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
