CREATE TABLE route
(
    id                       VARCHAR(36) PRIMARY KEY NOT NULL,
    airline_code             VARCHAR(4)              NOT NULL,
    airline_id               VARCHAR(36)             NOT NULL,
    source_airport_code      VARCHAR(4)              NOT NULL,
    source_airport_id        VARCHAR(36)             NOT NULL,
    destination_airport_code VARCHAR(4)              NOT NULL,
    destination_airport_id   VARCHAR(36)             NOT NULL,
    code_share               BOOLEAN                 NOT NULL DEFAULT TRUE,
    stops                    INTEGER                 NOT NULL DEFAULT 0,
    equipment                TEXT                    NOT NULL,
    price                    FLOAT,
    source_city_mapping      BIGINT                  NOT NULL, -- Used for flight route calculation
    destination_city_mapping BIGINT                  NOT NULL, -- Used for flight route calculation

    FOREIGN KEY (airline_id) REFERENCES airline (id),
    FOREIGN KEY (source_airport_id) REFERENCES airport (id),
    FOREIGN KEY (destination_airport_id) REFERENCES airport (id)
);

CREATE INDEX idx_source_city_mapping ON route (source_city_mapping);
CREATE INDEX idx_destination_city_mapping ON route (destination_city_mapping);
