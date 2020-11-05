package com.aaron.challenge.flightadvisor.comments.web;

import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.cities.CityService;
import com.aaron.challenge.flightadvisor.comments.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentMapperTest {

    @InjectMocks
    private CommentMapper commentMapper;

    @Mock
    private CityService cityService;

    @Test
    public void postToEntity_commentCreate_comment() {
        var city = new City();

        when(cityService.findById(eq("cityId"))).thenReturn(Optional.of(city));

        var commentCreate = new CommentCreate();
        commentCreate.setCityId("cityId");
        commentCreate.setDescription("description");

        var comment = commentMapper.postToEntity(commentCreate);

        assertThat(comment.getId()).isNotBlank();
        assertThat(comment.getCity()).isEqualTo(city);
        assertThat(comment.getDescription()).isEqualTo("description");
        assertThat(comment.getCreatedDate()).isNotNull();
        assertThat(comment.getModifiedDate()).isNotNull();
    }

    @Test
    public void putToEntity_commentCreate_target_comment() {
        var city = new City();

        when(cityService.findById(eq("cityId"))).thenReturn(Optional.of(city));

        var commentCreate = new CommentCreate();
        commentCreate.setCityId("cityId");
        commentCreate.setDescription("description");

        var target = new Comment();
        target.setCreatedDate(LocalDateTime.now());

        var comment = commentMapper.putToEntity(commentCreate, target);

        assertThat(comment.getCity()).isEqualTo(city);
        assertThat(comment.getDescription()).isEqualTo("description");
        assertThat(comment.getCreatedDate()).isNotNull();
        assertThat(comment.getModifiedDate()).isNotNull();
    }

    @Test
    public void patchToEntity_commentUpdate_target_comment() {
        var commentUpdate = new CommentUpdate();
        commentUpdate.setDescription("description");

        var target = new Comment();
        target.setCreatedDate(LocalDateTime.now());

        var comment = commentMapper.patchToEntity(commentUpdate, target);

        assertThat(comment.getDescription()).isEqualTo("description");
        assertThat(comment.getCreatedDate()).isNotNull();
        assertThat(comment.getModifiedDate()).isNotNull();
    }

    @Test
    public void searchToEntity_commentSearch_comment() {
        var commentSearch = new CommentSearch();
        commentSearch.setDescription("description");

        var comment = commentMapper.searchToEntity(commentSearch);

        assertThat(comment.getDescription()).isEqualTo("description");
    }

    @Test
    public void entityToResponse_comment_commentResponse() {
        var city = new City();
        city.setId("cityId");
        city.setName("name");
        city.setCountry("country");

        var comment = new Comment();
        comment.setId("id");
        comment.setCity(city);
        comment.setDescription("description");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());

        var commentResponse = commentMapper.entityToResponse(comment);

        assertThat(commentResponse.getId()).isEqualTo("id");
        assertThat(commentResponse.getCity().getId()).isEqualTo("cityId");
        assertThat(commentResponse.getCity().getName()).isEqualTo("name");
        assertThat(commentResponse.getCity().getCountry()).isEqualTo("country");
        assertThat(commentResponse.getDescription()).isEqualTo("description");
        assertThat(commentResponse.getCreatedDate()).isNotNull();
        assertThat(commentResponse.getModifiedDate()).isNotNull();
    }
}
