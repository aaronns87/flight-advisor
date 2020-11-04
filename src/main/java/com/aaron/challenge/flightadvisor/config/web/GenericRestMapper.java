package com.aaron.challenge.flightadvisor.config.web;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Generic mapper for mapping Entities and DTOs
 *
 * @param <ENTITY>   Database entity
 * @param <CREATE>   Create DTO
 * @param <UPDATE>   Update DTO
 * @param <SEARCH>   Search DTO
 * @param <RESPONSE> Response DTO
 */
public interface GenericRestMapper<ENTITY, CREATE, UPDATE, SEARCH, RESPONSE> {

    /**
     * Map Create DTO from POST to Entity.
     *
     * @param create Create DTO
     * @return Mapped Create DTO to Entity
     */
    ENTITY postToEntity(CREATE create);

    /**
     * Map Create DTO from PUT to Entity.
     *
     * @param create Create DTO
     * @param target Target Entity to map over (include null values when mapping)
     * @return Target Entity with values mapped over from Create DTO
     */
    ENTITY putToEntity(CREATE create, ENTITY target);

    /**
     * Map Update DTO from PATCH to Entity.
     *
     * @param update Update DTO
     * @param target Target Entity to map over (ignore null values when mapping)
     * @return Target Entity with values mapped over from Update DTO
     */
    ENTITY patchToEntity(UPDATE update, ENTITY target);

    /**
     * Map Search DTO from SEARCH to Entity.
     *
     * @param search Search DTO
     * @return Mapped Search DTO to Entity
     */
    ENTITY searchToEntity(SEARCH search);

    /**
     * Map Entity to Response DTO. Used for POST, PUT, PATCH and GETs.
     *
     * @param entity Entity
     * @return Mapped Entity to Response DTO
     */
    RESPONSE entityToResponse(ENTITY entity);

    /**
     * Map Entity page to Response DTO page.
     *
     * @param entities Page of Entities
     * @return Page of Response DTOs
     */
    default Page<RESPONSE> entityToResponsePage(Page<ENTITY> entities) {
        return entities.map(this::entityToResponse);
    }

    /**
     * Map Entity list to Response DTO list.
     *
     * @param entities List of Entities
     * @return List of Response DTOs
     */
    default List<RESPONSE> entityToResponseList(List<ENTITY> entities) {
        return entities.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    /**
     * Get value from GETTER method and set it to SETTER if value is not null
     *
     * @param supplierGetter Getter method
     * @param consumerSetter Setter method
     * @param <T>            Parameter type
     */
    default <T> void setIfNotNull(Supplier<T> supplierGetter, Consumer<T> consumerSetter) {
        if (isNotNull(supplierGetter.get())) {
            consumerSetter.accept(supplierGetter.get());
        }
    }

    /**
     * Is value null.
     *
     * @param value Value to test
     * @return Is value null
     */
    default boolean isNull(Object value) {
        return Objects.isNull(value);
    }

    /**
     * Is value not null.
     *
     * @param value Value to test
     * @return Is value not null
     */
    default boolean isNotNull(Object value) {
        return !Objects.isNull(value);
    }
}

