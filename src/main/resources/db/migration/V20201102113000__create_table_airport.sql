CREATE TABLE airport (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(255) NOT NULL,
  city_id BIGINT NOT NULL,
  iata VARCHAR(3),
  icao VARCHAR(4),
  latitude VARCHAR(255),
  longitude VARCHAR(255),
  altitude INTEGER,
  time_zone DECIMAL NOT NULL,
  dst VARCHAR(1) NOT NULL,
  tz VARCHAR(255),
  type VARCHAR(255) NOT NULL,
  source VARCHAR(255) NOT NULL,

  FOREIGN KEY (city_id) REFERENCES city(id)
);