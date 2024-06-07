package com.example.WebApp.controller;

import com.example.WebApp.AniLog;
import com.example.WebApp.AniLogReview;
import com.example.WebApp.AniLogServiceIf;
import com.example.WebApp.User;
import com.example.WebApp.annictapi.Anime;
import com.example.WebApp.annictapi.AnimeServiceIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.*;

@Controller
public class WebAppController {
    @Autowired
    private AnimeServiceIf animeServiceIf;

    @Autowired
    private AniLogServiceIf aniLogServiceIf;

    @Autowired
    private HttpSession session;

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @PostMapping("api/user")
    public ResponseEntity<Integer> login(@RequestBody User user) {
        System.out.println(user);
        var userResult = aniLogServiceIf.findByUser(user.getUserName(), user.getPass());
        int result = userResult == null ? -1 : 0;
        if (result == 0) {
            session.setAttribute("user", userResult);
        }
        try {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("anilog")
    public String anilog() {
        if (session.getAttribute("user") == null) {
            return "redirect:/index";
        }
        return "anilog";
    }

    @GetMapping("api/anilog")
    public ResponseEntity<List<Anime>> getAnime(@RequestParam(name="user_id") int userId) {
        var allAniLog = aniLogServiceIf.findAllAniLog(userId);

        List<Anime> animeList = new ArrayList<>();
        for (AniLog anilog : allAniLog) {
            animeList.add( animeServiceIf.searchAnimeId(anilog.getAnime_id()) );
        }
        try {
            return new ResponseEntity<>(animeList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("api/anilog/detail")
    public ResponseEntity<Map<String, Object>> getAnilog(@RequestParam(name="anime_id") int animeId, @RequestParam(name="user_id") int userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("anime", animeServiceIf.searchAnimeId(animeId));
        map.put("review", aniLogServiceIf.findByAniLogAnimeId(animeId, userId));
        try {
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("api/anilog/insert")
    public ResponseEntity<Anime> postAnilog(@RequestBody AniLogReview review) {
        System.out.println(review);
        aniLogServiceIf.insert(review.getAnimeId(), review.getRate(), review.getComment(), review.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("api/anilog/update")
    public ResponseEntity<Anime> putAnilog(@RequestBody AniLogReview review) {
        System.out.println(review);
        aniLogServiceIf.update(review.getAnimeId(), review.getRate(), review.getComment(), review.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("api/anilog/delete")
    public ResponseEntity<Anime> deleteAnilog(@RequestBody AniLogReview review) {
        System.out.println(review);
        aniLogServiceIf.delete(review.getAnimeId(), review.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("api/anime")
    public ResponseEntity<Map<String, Object>> getAnime(
            @RequestParam(name="title") String title,
            @RequestParam(name="year") String year,
            @RequestParam(name="season") String season,
            @RequestParam(name="sort") String sort,
            @RequestParam(name="page") int page) {
        Map<String, Object> map = animeServiceIf.searchAnime(title, year, season, sort, page);
        List<Anime> animeList = (List<Anime>) map.get("animeList");
        int totalCount = (int) map.get("totalCount");
        int nextPage = (int) map.get("nextPage");
        int prevPage = (int) map.get("prevPage");

        for (Anime anime : Objects.requireNonNull(animeList)) {
            System.out.println(
                    "ID:" + anime.getId() +
                    " TITLE:" + anime.getTitle() +
                    " MEDIA:" + anime.getMedia() +
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

    @GetMapping("logout")
    public String logout() {
        if (session.getAttribute("user") == null) {
            return "redirect:/index";
        }
        session.invalidate();
        return "index";
    }

}
