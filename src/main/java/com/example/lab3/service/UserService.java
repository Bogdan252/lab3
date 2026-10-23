package com.example.lab3.service;

import com.example.lab3.entity.User;
import com.example.lab3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            if (updatedUser.getName() != null) {
                user.setName(updatedUser.getName());
            }
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}