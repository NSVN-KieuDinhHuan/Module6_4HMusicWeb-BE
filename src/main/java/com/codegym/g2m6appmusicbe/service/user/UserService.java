package com.codegym.g2m6appmusicbe.service.user;

import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

}
