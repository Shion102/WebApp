package com.example.WebApp;

import java.util.List;

public interface AniLogServiceIf {
    List<AniLog> findAllAniLog(int userId);

    AniLog findByAniLogAnimeId(int animeId, int userId);

    int insert(int animeId, int rate, String comment, int userId);
}
