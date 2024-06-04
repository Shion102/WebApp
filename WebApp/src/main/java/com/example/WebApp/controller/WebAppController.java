package com.example.WebApp.controller;

import com.example.WebApp.annictapi.Anime;
import com.example.WebApp.annictapi.AnimeServiceIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class WebAppController {
    @Autowired
    private AnimeServiceIf animeServiceIf;

    @GetMapping("index")
    public String index() {
        Map<String, Object> map = animeServiceIf.searchAnime("ハイキュー", "", "", 1);
        List<Anime> animeList = (List<Anime>) map.get("animeList");
        int totalCount = (int) map.get("totalCount");
        int nextPage = (int) map.get("nextPage");
        int prevPage = (int) map.get("prevPage");

        for (Anime anime : Objects.requireNonNull(animeList)) {
            System.out.println(
                    "ID:" + anime.getId() +
                    " TITLE:" + anime.getTitle() +
                    " MEDIA:" + anime.getMedia() +
                    " OFFICIALSITE:" + anime.getOfficialSite() +
                    " IMAGEURL:" + anime.getOgImageUrl() +
                    " SEASON:" + anime.getSeason());
        }
        System.out.println(totalCount + " " + prevPage + " " + nextPage);
        return "index";
    }


    @GetMapping("api/anime")
    public ResponseEntity<Map<String, Object>> getAnime(
            @RequestParam(name="title") String title,
            @RequestParam(name="year") String year,
            @RequestParam(name="season") String season,
            @RequestParam(name="page") int page) {
        Map<String, Object> map = animeServiceIf.searchAnime(title, year, season, page);
        List<Anime> animeList = (List<Anime>) map.get("animeList");
        int totalCount = (int) map.get("totalCount");
        int nextPage = (int) map.get("nextPage");
        int prevPage = (int) map.get("prevPage");

        for (Anime anime : Objects.requireNonNull(animeList)) {
            System.out.println(
                    "ID:" + anime.getId() +
                            " TITLE:" + anime.getTitle() +
                            " MEDIA:" + anime.getMedia() +
                            " OFFICIALSITE:" + anime.getOfficialSite() +
                            " IMAGEURL:" + anime.getOgImageUrl() +
                            " SEASON:" + anime.getSeason());
        }
        System.out.println(totalCount + " " + prevPage + " " + nextPage);
        try {
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
