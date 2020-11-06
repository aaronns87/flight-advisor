CREATE TABLE "user"
(
    id         VARCHAR(36) PRIMARY KEY NOT NULL,
    role       VARCHAR(20)             NOT NULL,
    first_name VARCHAR(255)            NOT NULL,
    last_name  VARCHAR(255)            NOT NULL,
    user_name  VARCHAR(255)            NOT NULL UNIQUE,
    password   VARCHAR(255)            NOT NULL
);