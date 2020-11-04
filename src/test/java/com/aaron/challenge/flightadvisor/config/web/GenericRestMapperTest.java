package com.aaron.challenge.flightadvisor.config.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GenericRestMapperTest {

    private final GenericTestMapper genericTestMapper = new GenericTestMapper();

    @Test
    public void postToEntity_post_to_entity() {
        var create = new SomeCreate();
        create.setCreateField("create");

        assertThat(genericTestMapper.postToEntity(create).getEntityField()).isEqualTo("create");
    }

    @Test
    public void putToEntity_put_to_entity() {
        var entity = new SomeEntity();
        entity.setEntityField("entity");

        var create = new SomeCreate();
        create.setCreateField("create");

        assertThat(genericTestMapper.putToEntity(create, entity).getEntityField()).isEqualTo("create");
    }

    @Test
    public void patchToEntity_patch_to_entity() {
        var entity = new SomeEntity();
        entity.setEntityField("entity");

        var update = new SomeUpdate();
        update.setUpdateField("update");

        assertThat(genericTestMapper.patchToEntity(update, entity).getEntityField()).isEqualTo("update");
    }

    @Test
    public void searchToEntity_search_to_entity() {
        var search = new SomeSearch();
        search.setSearchField("search");

        assertThat(genericTestMapper.searchToEntity(search).getEntityField()).isEqualTo("search");
    }

    @Test
    public void entityToResponse_entity_to_response() {
        var entity = new SomeEntity();
        entity.setEntityField("entity");

        assertThat(genericTestMapper.entityToResponse(entity).getGetField()).isEqualTo("entity");
    }

    @Test
    public void entityToResponsePage() {
        assertThat(genericTestMapper.entityToResponsePage(Page.empty())).isInstanceOf(Page.class);
    }

    @Test
    public void isNull_null_true() {
        assertThat(genericTestMapper.isNull(null)).isTrue();
    }

    @Test
    public void isNull_notNull_false() {
        assertThat(genericTestMapper.isNull("notNull")).isFalse();
    }

    @Test
    public void isNotNull_null_false() {
        assertThat(genericTestMapper.isNotNull(null)).isFalse();
    }

    @Test
    public void isNotNull_notNull_true() {
        assertThat(genericTestMapper.isNotNull("notNull")).isTrue();
    }

    private class GenericTestMapper implements GenericRestMapper<SomeEntity, SomeCreate, SomeUpdate, SomeSearch, SomeGet> {

        @Override
        public SomeEntity postToEntity(SomeCreate create) {
            var entity = new SomeEntity();
            entity.setEntityField(create.getCreateField());
            return entity;
        }

        @Override
        public SomeEntity putToEntity(SomeCreate create, SomeEntity target) {
            var entity = new SomeEntity();
            entity.setEntityField(create.getCreateField());
            return entity;
        }

        @Override
        public SomeEntity patchToEntity(SomeUpdate update, SomeEntity target) {
            target.setEntityField(update.getUpdateField());
            return target;
        }

        @Override
        public SomeEntity searchToEntity(SomeSearch someSearch) {
            var entity = new SomeEntity();
            entity.setEntityField(someSearch.getSearchField());
            return entity;
        }

        @Override
        public SomeGet entityToResponse(SomeEntity someEntity) {
            var get = new SomeGet();
            get.setGetField(someEntity.getEntityField());
            return get;
        }
    }

    private class SomeEntity {

        private String entityField;

        public String getEntityField() {
            return entityField;
        }

        public void setEntityField(String entityField) {
            this.entityField = entityField;
        }
    }

    private class SomeCreate {

        private String createField;

        public String getCreateField() {
            return createField;
        }

        public void setCreateField(String createField) {
            this.createField = createField;
        }
    }

    private class SomeUpdate {

        private String updateField;

        public String getUpdateField() {
            return updateField;
        }

        public void setUpdateField(String updateField) {
            this.updateField = updateField;
        }
    }

    private class SomeSearch {

        private String searchField;

        public String getSearchField() {
            return searchField;
        }

        public void setSearchField(String searchField) {
            this.searchField = searchField;
        }
    }

    private class SomeGet {

        private String getField;

        public String getGetField() {
            return getField;
        }

        public void setGetField(String getField) {
            this.getField = getField;
        }
    }
}
