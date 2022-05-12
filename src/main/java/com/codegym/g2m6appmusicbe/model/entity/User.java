package com.codegym.g2m6appmusicbe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "Varchar(255)", nullable = false, unique = true)
    private String username;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String password;
    @Column( nullable = false, unique = true)
    private String phoneNumber;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String address;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private List<Role> roles;
    public User(String username, String password, String phoneNumber, String address, String image) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.image = image;
    }
}
