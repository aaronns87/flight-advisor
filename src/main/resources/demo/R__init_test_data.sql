TRUNCATE
    route, airline, airport, comment, city, "user"
RESTART IDENTITY CASCADE;

INSERT INTO "user" (id, "role", first_name, last_name, user_name, password)
    VALUES (1, 'ADMIN', 'Admin', 'Admin', 'admin', '$2a$10$GunqFZe149kg7rIwlXmHfOmcL105CZ.HzK2QbbbXKGHPiZxB7TyI.');

INSERT INTO "user" (id, "role", first_name, last_name, user_name, password)
    VALUES (2, 'USER', 'User', 'User', 'user', '$2a$10$LIqs6VWrERgP5puLrYVNZOab7ID3vhim576bbQ436H/CxgsUjz/cC');

