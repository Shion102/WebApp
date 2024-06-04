package com.example.WebApp.annictapi;

import java.util.HashMap;

public interface AnimeDaoIf {
    HashMap<String, Object> searchAnime(String name, String year, String season, int page);
}
