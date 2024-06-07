package com.example.WebApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AniLogDao implements AniLogDaoIf {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<AniLog> findAllAniLog(int userId) {
        var param = new MapSqlParameterSource();
        param.addValue("userId", userId);
        return jdbcTemplate.query("""
                SELECT anilog.id, anime_id, rate, comment, user_id, name
                FROM anilog
                INNER JOIN users ON user_id = users.id
                WHERE user_id = :userId
                ORDER BY anilog.id""",
                param, new DataClassRowMapper<>(AniLog.class));
    }

    @Override
    public AniLog findByAniLogAnimeId(int animeId, int userId) {
        var param = new MapSqlParameterSource();
        param.addValue("animeId", animeId);
        param.addValue("userId", userId);
        List<AniLog> list = jdbcTemplate.query("""
                SELECT anilog.id, anime_id, rate, comment, user_id, name
                FROM anilog
                INNER JOIN users ON user_id = users.id
                WHERE anime_id = :animeId AND user_id = :userId""",
                param, new DataClassRowMapper<>(AniLog.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int insert(int animeId, int rate, String comment, int userId) {
        var param = new MapSqlParameterSource();
        param.addValue("animeId", animeId);
        param.addValue("rate", rate);
        param.addValue("comment", comment);
        param.addValue("userId", userId);
        return jdbcTemplate.update("""
                        INSERT INTO anilog
                        (anime_id, rate, comment, user_id)
                        VALUES
                        (:animeId, :rate, :comment, :userId)""", param);
    }

    @Override
    public int update(int animeId, int rate, String comment, int userId) {
        var param = new MapSqlParameterSource();
        param.addValue("animeId", animeId);
        param.addValue("rate", rate);
        param.addValue("comment", comment);
        param.addValue("userId", userId);
        return jdbcTemplate.update("""
                      UPDATE anilog
                      SET rate = :rate,
                          comment = :comment
                      WHERE anime_id = :animeId AND user_id = :userId
                      """, param);
    }

    @Override
    public int delete(int animeId, int userId) {
        var param = new MapSqlParameterSource();
        param.addValue("animeId", animeId);
        param.addValue("userId", userId);
        return jdbcTemplate.update("""
                      DELETE FROM anilog
                      WHERE anime_id = :animeId AND user_id = :userId
                      """, param);
    }

    @Override
    public User findByUser(String userName, String pass) {
        var param = new MapSqlParameterSource();
        param.addValue("userName", userName);
        param.addValue("pass", pass);
        List<User> list = jdbcTemplate.query("""
                SELECT * FROM users
                WHERE username = :userName AND pass = :pass""",
                param, new DataClassRowMapper<>(User.class));;
        return list.isEmpty() ? null : list.get(0);
    }
}
