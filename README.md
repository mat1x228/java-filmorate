# java-filmorate



## Описание базы данных

Данная база данных предназначена для хранения информации о фильмах, пользователях и их взаимодействиях. Она состоит из следующих таблиц:

1. **mpa** - таблица, содержащая информацию о рейтингах фильмов.
2. **films** - таблица, содержащая информацию о фильмах.
3. **genres** - таблица, содержащая жанры фильмов.
4. **filmgenres** - таблица, связывающая фильмы и их жанры.
5. **users** - таблица, содержащая информацию о пользователях.
6. **friends** - таблица, представляющая дружеские связи между пользователями.
7. **likes** - таблица, содержащая информацию о том, какие фильмы понравились пользователям.

## Основные запросы к таблицам

### 1. Таблица mpa

#### Запрос: Получить все рейтинги
```
SELECT * FROM mpa;
```

#### Запрос: Добавить новый рейтинг
```
INSERT INTO mpa (name) VALUES ('PG-13');
```

### 2. Таблица films

#### Запрос: Получить все фильмы
```
SELECT * FROM films;
```

#### Запрос: Найти фильм по названию
```
SELECT * FROM films WHERE name = 'Название фильма';
```

### 3. Таблица genres

#### Запрос: Получить все жанры
```
SELECT * FROM genres;
```

#### Запрос: Добавить новый жанр
```
INSERT INTO genres (name) VALUES ('Комедия');
```

### 4. Таблица filmgenres

#### Запрос: Получить все жанры для конкретного фильма
```
SELECT g.name
FROM filmgenres fg
JOIN genres g ON fg.genreid = g.id
WHERE fg.filmid = 1;
```

#### Запрос: Добавить жанр к фильму
```
INSERT INTO filmgenres (filmid, genreid) VALUES (1, 2); 
```

### 5. Таблица users

#### Запрос: Получить всех пользователей
```
SELECT * FROM users;
```

#### Запрос: Найти пользователя по email
```
SELECT * FROM users WHERE email = 'example@example.com';
```

### 6. Таблица friends

#### Запрос: Получить список друзей пользователя
```
SELECT u.name
FROM friends f
JOIN users u ON f.friendid = u.id
WHERE f.userid = 1;
```

#### Запрос: Добавить друга
```
INSERT INTO friends (userid, friendid) VALUES (1, 2); 
```

### 7. Таблица likes

#### Запрос: Получить все фильмы, которые понравились пользователю
```
SELECT f.name
FROM likes l
JOIN films f ON l.filmid = f.id
WHERE l.userid = 1;
```

#### Запрос: Поставить лайк фильму
```
INSERT INTO likes (userid, filmid) VALUES (1, 2); 
```
