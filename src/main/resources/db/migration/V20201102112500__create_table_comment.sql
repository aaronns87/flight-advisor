CREATE TABLE comment (
  id BIGINT PRIMARY KEY NOT NULL,
  city_id BIGINT NOT NULL,
  description TEXT NOT NULL,
  created_date TIMESTAMP NOT NULL,
  modified_date TIMESTAMP NOT NULL,

  FOREIGN KEY (city_id) REFERENCES city(id)
);