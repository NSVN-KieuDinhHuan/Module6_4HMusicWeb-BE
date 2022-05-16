package com.codegym.g2m6appmusicbe.model.dto;

import com.codegym.g2m6appmusicbe.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongForm {
    private Long id;
    private String name;
    private String description;
    private MultipartFile mp3File;
    private MultipartFile image;
    private String author;
    private User user;
    private Category category;
    private Album album;
    private Tag tag;
    private long views;
    private Artist artist;
}