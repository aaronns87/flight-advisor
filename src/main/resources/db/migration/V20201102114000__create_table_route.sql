CREATE TABLE route (
  id VARCHAR(36) PRIMARY KEY NOT NULL,
  airline_code VARCHAR(3) NOT NULL,
  airline_id VARCHAR(36) NOT NULL,
  source_airport_code VARCHAR(3) NOT NULL,
  source_airport_id VARCHAR(36) NOT NULL,
  destination_airport_code VARCHAR(3) NOT NULL,
  destination_airport_id VARCHAR(36) NOT NULL,
  code_share BOOLEAN NOT NULL DEFAULT TRUE,
  stops INTEGER NOT NULL DEFAULT 0,
  equipment VARCHAR(3) NOT NULL,
  price FLOAT,

  FOREIGN KEY (airline_id) REFERENCES airline(id),
  FOREIGN KEY (source_airport_id) REFERENCES airport(id),
  FOREIGN KEY (destination_airport_id) REFERENCES airport(id)
);