package com.codegym.g2m6appmusicbe.repository;


import com.codegym.g2m6appmusicbe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
