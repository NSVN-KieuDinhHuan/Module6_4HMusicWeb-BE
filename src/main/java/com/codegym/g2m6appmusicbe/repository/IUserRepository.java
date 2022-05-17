package com.codegym.g2m6appmusicbe.repository;


import com.codegym.g2m6appmusicbe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}
