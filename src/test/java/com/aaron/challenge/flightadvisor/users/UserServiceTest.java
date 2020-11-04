package com.aaron.challenge.flightadvisor.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void findById() {
        var user = new User();

        when(userRepository.findById(eq("id"))).thenReturn(Optional.of(user));

        assertThat(userService.findById("id")).isPresent();
    }

    @Test
    public void findByUserName() {
        var user = new User();

        when(userRepository.findByUserName(eq("userName"))).thenReturn(Optional.of(user));

        assertThat(userService.findByUserName("userName")).isPresent();
    }

    @Test
    public void create() {
        var user = new User();
        user.setPassword("password");

        when(passwordEncoder.encode(eq("password"))).thenReturn("encodedPassword");
        when(userRepository.save(eq(user))).thenReturn(user);

        assertThat(userService.create(user).getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    public void update() {
        var user = new User();
        user.setPassword("password");

        when(passwordEncoder.encode(eq("password"))).thenReturn("encodedPassword");
        when(userRepository.save(eq(user))).thenReturn(user);

        assertThat(userService.update(user).getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    public void search() {
        var user = new User();

        when(userRepository.findAll(isA(Example.class), isA(Pageable.class))).thenReturn(new PageImpl(List.of(user)));

        assertThat(userService.search(user, Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void findAll() {
        var user = new User();

        when(userRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl(List.of(user)));

        assertThat(userService.findAll(Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void delete() {
        var user = new User();

        userService.delete(user);

        verify(userRepository).delete(user);
    }

}
