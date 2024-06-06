package com.example.WebApp.annictapi;

import java.util.HashMap;
import java.util.List;

public interface AnimeServiceIf {
    HashMap<String, Object> searchAnime(String name, String year, String season, String sort, int page);
    Anime searchAnimeId(int id);


}