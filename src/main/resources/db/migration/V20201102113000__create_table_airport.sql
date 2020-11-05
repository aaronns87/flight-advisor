CREATE TABLE airport (
  id VARCHAR(36) PRIMARY KEY NOT NULL,
  external_id BIGINT UNIQUE,
  name VARCHAR(255) NOT NULL,
  city_id VARCHAR(36) NOT NULL,
  iata VARCHAR(3),
  icao VARCHAR(4),
  latitude VARCHAR(64),
  longitude VARCHAR(64),
  altitude INTEGER,
  time_zone DECIMAL NOT NULL,
  dst VARCHAR(1) NOT NULL,
  tz VARCHAR(64),
  type VARCHAR(64) NOT NULL,
  source VARCHAR(255) NOT NULL,

  FOREIGN KEY (city_id) REFERENCES city(id)
);