package com.example.WebApp.annictapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class AnimeDao implements AnimeDaoIf {

    @Override
    public HashMap<String, Object> searchAnime(String name, String year, String season, int page) {
        final String API_URL = "https://api.annict.com/v1/";
        final String validSeason = year.isEmpty() | season.isEmpty() ? null : year + "-" + season;
        final String API_FIELD = String.format(
                """
                works?\
                fields=id,title,media_text,official_site_url,season_name_text,images&\
                filter_title=%s&\
                filter_season=%s&\
                per_page=50&\
                sort_season=asc&\
                """, name, year, validSeason);
        final String API_PAGE = "page=" + page + "&";
        final String AUTH_TOKEN = "access_token=aYE3_-ioQ_P5AUx0JzNKlsAMiD0yAlQ3yo_HreWm0do";

        String url = API_URL + API_FIELD + API_PAGE + AUTH_TOKEN;

        System.out.println(url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        try {
            String responseBody;
            try (Response response = client.newCall(request).execute()) {
                responseBody = Objects.requireNonNull(response.body()).string();
            }

            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray worksArray = jsonObject.getJSONArray("works");

            List<Anime> animeList = new ArrayList<>();
            for (int i = 0; i < worksArray.length(); i++) {
                JSONObject workObject = worksArray.getJSONObject(i);
                Anime anime = new Anime();
                anime.setId(workObject.getInt("id"));
                anime.setTitle(workObject.getString("title"));
                anime.setMedia(workObject.getString("media_text"));
                anime.setOfficialSite(workObject.getString("official_site_url"));
                anime.setSeason(workObject.getString("season_name_text"));
                String ogImageUrl = workObject.getJSONObject("images")
                        .getJSONObject("facebook")
                        .getString("og_image_url");
                anime.setOgImageUrl(ogImageUrl);

                animeList.add(anime);
            }

            int totalCount;
            try {
                totalCount = jsonObject.getInt("total_count");
            } catch (JSONException e) {
                totalCount = -1;
            }

            int nextPage;
            try {
                nextPage = jsonObject.getInt("next_page");
            } catch (JSONException e) {
                nextPage = -1;
            }

            int prevPage;
            try {
                prevPage = jsonObject.getInt("prev_page");
            } catch (JSONException e) {
                prevPage = -1;
            }

            HashMap<String, Object> map = new HashMap<>();
            map.put("animeList", animeList);
            map.put("totalCount", totalCount);
            map.put("nextPage", nextPage);
            map.put("prevPage", prevPage);
            return map;
        } catch (IOException e) {
            return null;
        }
    }

}
