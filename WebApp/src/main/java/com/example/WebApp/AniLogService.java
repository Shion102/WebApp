package com.example.WebApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AniLogService implements AniLogServiceIf {
    @Autowired
    private AniLogDao aniLogDao;

    @Override
    public List<AniLog> findAllAniLog(int userId) {
        return aniLogDao.findAllAniLog(userId);
    }

    @Override
    public AniLog findByAniLogAnimeId(int animeId, int userId){
        return aniLogDao.findByAniLogAnimeId(animeId, userId);
    }

    @Override
    public int insert(int animeId, int rate, String comment, int userId) {
        return aniLogDao.insert(animeId, rate, comment, userId);
    }

    @Override
    public int update(int animeId, int rate, String comment, int userId) {
        return aniLogDao.update(animeId, rate, comment, userId);
    }

    @Override
    public int delete(int animeId, int userId) {
        return aniLogDao.delete(animeId, userId);
    }

    @Override
    public User findByUser(String userName, String pass) {
        return aniLogDao.findByUser(userName, pass);
    }
}
