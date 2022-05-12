package com.codegym.g2m6appmusicbe.service.user;

import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User findByUsername(String username);
}
