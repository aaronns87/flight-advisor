CREATE TABLE comment (
  id VARCHAR(36) PRIMARY KEY NOT NULL,
  city_id VARCHAR(36) NOT NULL,
  description TEXT NOT NULL,
  created_date TIMESTAMP NOT NULL,
  modified_date TIMESTAMP NOT NULL,

  FOREIGN KEY (city_id) REFERENCES city(id)
);