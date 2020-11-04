package com.aaron.challenge.flightadvisor.comments;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    public void findById() {
        var comment = new Comment();

        when(commentRepository.findById(eq("id"))).thenReturn(Optional.of(comment));

        assertThat(commentService.findById("id")).isPresent();
    }

    @Test
    public void create() {
        var comment = new Comment();

        when(commentRepository.save(eq(comment))).thenReturn(comment);

        assertThat(commentService.create(comment)).isEqualTo(comment);
    }

    @Test
    public void update() {
        var comment = new Comment();

        when(commentRepository.save(eq(comment))).thenReturn(comment);

        assertThat(commentService.update(comment)).isEqualTo(comment);
    }

    @Test
    public void search() {
        var comment = new Comment();

        when(commentRepository.findAll(isA(Example.class), isA(Pageable.class))).thenReturn(new PageImpl(List.of(comment)));

        assertThat(commentService.search(comment, Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void findAll() {
        var comment = new Comment();

        when(commentRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl(List.of(comment)));

        assertThat(commentService.findAll(Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void delete() {
        var comment = new Comment();

        commentService.delete(comment);

        verify(commentRepository).delete(comment);
    }
}
