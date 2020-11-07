TRUNCATE
    route, airline, airport, comment, city, "user"
    RESTART IDENTITY CASCADE;

INSERT INTO "user" (id, "role", first_name, last_name, user_name, password)
VALUES ('e7ec352c-78b5-45ae-8381-9d4fa02c1324', 'ADMIN', 'Admin', 'Admin', 'admin', '$2a$10$GunqFZe149kg7rIwlXmHfOmcL105CZ.HzK2QbbbXKGHPiZxB7TyI.');

INSERT INTO "user" (id, "role", first_name, last_name, user_name, password)
VALUES ('36528ce2-546e-4f78-b196-0f1f19042a3c', 'USER', 'User', 'User', 'user', '$2a$10$LIqs6VWrERgP5puLrYVNZOab7ID3vhim576bbQ436H/CxgsUjz/cC');

