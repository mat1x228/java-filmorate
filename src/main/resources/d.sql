--INSERT INTO genres (id, name) VALUES (1, 'Комедия');
--INSERT INTO genres (id, name) VALUES (2, 'Драма');
--INSERT INTO genres (id, name) VALUES (3, 'Мультфильм');
--INSERT INTO genres (id, name) VALUES (4, 'Триллер');
--INSERT INTO genres (id, name) VALUES (5, 'Документальный');
--INSERT INTO genres (id, name) VALUES (6, 'Боевик');
--
--INSERT INTO mpa (id, name) VALUES (1, 'G');
--INSERT INTO mpa (id, name) VALUES (2, 'PG');
--INSERT INTO mpa (id, name) VALUES (3, 'PG-13');
--INSERT INTO mpa (id, name) VALUES (4, 'R');
--INSERT INTO mpa (id, name) VALUES (5, 'NC-17');

--INSERT INTO films (id, name, description, releaseDate, duration, mpaid)
--VALUES
--(11, 'The Matrix', 'A computer hacker learns about the true nature of his reality and his role in the war against its controllers.', '1999-03-31', 136, 1),
--(12, 'Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.', '2010-07-16', 148, 2);
--
--INSERT INTO filmgenres (filmid, genreid) VALUES (11, 1);
--INSERT INTO filmgenres (filmid, genreid) VALUES (12, 2);
--
--INSERT INTO users (email, login, name, birthday) VALUES
--('testuser1@example.com', 'testuser1', 'Test User One', '1990-01-01'),
--('testuser2@example.com', 'testuser2', 'Test User Two', '1992-02-02');
--
--INSERT INTO friends (userid, friendid) VALUES (1, 2);
--INSERT INTO friends (userid, friendid) VALUES (2, 1);
