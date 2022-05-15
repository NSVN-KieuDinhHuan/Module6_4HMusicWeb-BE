package com.codegym.g2m6appmusicbe.service.user;

import com.codegym.g2m6appmusicbe.model.entity.Role;

import com.codegym.g2m6appmusicbe.model.dto.UserPrincipal;
import com.codegym.g2m6appmusicbe.model.entity.User;
import com.codegym.g2m6appmusicbe.model.dto.UserPrincipal;
import com.codegym.g2m6appmusicbe.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        String password = user.getPassword();
        String encoderPassword = passwordEncoder.encode(password);
        user.setPassword(encoderPassword);
        List<Role> roles = new ArrayList<>();
        Role role = new Role(2L,"ROLE_USER");
        roles.add(role);
        user.setRoles(roles);
        User newUser = userRepository.save(user);
        return userRepository.save(user);
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return UserPrincipal.build(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
