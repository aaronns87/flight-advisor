CREATE TABLE airline
(
    id          VARCHAR(36) PRIMARY KEY NOT NULL,
    external_id BIGINT UNIQUE,
    code        VARCHAR(4)              NOT NULL
);