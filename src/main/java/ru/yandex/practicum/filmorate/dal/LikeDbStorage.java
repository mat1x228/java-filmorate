//package ru.yandex.practicum.filmorate.dal;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//import ru.yandex.practicum.filmorate.storage.likes.LikeStorage;
//
//@Repository("likeDbStorage")
//@Qualifier("likeDbStorage")
//@Slf4j
//@Component
//public class LikeDbStorage implements LikeStorage {
//    private final JdbcTemplate jdbc;
//    private final LikesRowMapper mapper;
//    private static final String FIND_BY_ID_QUERY = "SELECT userid FROM likes WHERE filmid = ?";
//    private static final String DELETE_INTO_LIKES_QUERY = "DELETE FROM likes WHERE filmid = ? AND userid = ?";
//    private static final String INSERT_LIKE_BY_ID_QUERY = "INSERT INTO likes (filmid, userid) VALUES  (?,?) ";
//
//    @Autowired
//    public LikeDbStorage(JdbcTemplate jdbc, LikesRowMapper mapper) {
//        this.jdbc = jdbc;
//        this.mapper = mapper;
//    }
//}
