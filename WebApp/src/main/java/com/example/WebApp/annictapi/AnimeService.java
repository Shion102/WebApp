package com.example.WebApp.annictapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AnimeService implements AnimeServiceIf {
    @Autowired
    private AnimeDao animeDao;

    @Override
    public HashMap<String, Object> searchAnime(String name, String year, String season, String sort, int page) {
        return animeDao.searchAnime(name, year, season, sort, page);
    }

    @Override
    public Anime searchAnimeId(int id) {
        return animeDao.searchAnimeId(id);
    }


}
