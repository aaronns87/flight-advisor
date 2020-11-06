CREATE TABLE city
(
    id      VARCHAR(36) PRIMARY KEY NOT NULL,
    name    VARCHAR(255)            NOT NULL,
    country VARCHAR(255)            NOT NULL,
    mapping BIGINT                  NOT NULL DEFAULT NEXTVAL('city_sequence'),
    UNIQUE (name, country)
);