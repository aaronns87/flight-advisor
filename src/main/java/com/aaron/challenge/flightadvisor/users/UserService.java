package com.aaron.challenge.flightadvisor.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor

@Service
public class UserService {

    public static final ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnorePaths("id")
            .withIgnoreNullValues()
            .withIgnoreCase(true)
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User create(User user) {
        user.setPassword(encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User update(User user) {
        user.setPassword(encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Page<User> search(User user, Pageable pageable) {
        return userRepository.findAll(Example.of(user, matcher), pageable);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    private String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
