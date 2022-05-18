package com.codegym.g2m6appmusicbe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "artists")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean gender;
    private Date dateOfBirth;
    private String story;
    @ManyToOne
    private Category category;
    private String band;
    private String moreInfo;
    //    @ManyToMany
//    private List<Song> songs;
    private int comments;
    private int likes;
}
