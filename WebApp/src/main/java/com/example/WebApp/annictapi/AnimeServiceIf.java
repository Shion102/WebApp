package com.example.WebApp.annictapi;

import java.util.HashMap;

public interface AnimeServiceIf {
    HashMap<String, Object> searchAnime(String name, String year, String season, int page);
}