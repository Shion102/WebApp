package com.example.WebApp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        final String API_URL = "https://api.annict.com/v1/";
        final String API_FIELD = """
            works?
            fields=id,title,media_text,official_site_url,season_name_text,images&
            filter_season=2024-spring&
            per_page=50&
            sort_season=asc&
            page=1
            """;
        final String AUTH_TOKEN = "&access_token=aYE3_-ioQ_P5AUx0JzNKlsAMiD0yAlQ3yo_HreWm0do";

        String url = API_URL + API_FIELD + AUTH_TOKEN;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

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

        for (Anime anime : animeList) {
            System.out.println(
                    "ID:" + anime.getId() +
                            " TITLE:" + anime.getTitle() +
                            " MEDIA:" + anime.getMedia() +
                            " OFFICIALSITE:" + anime.getOfficialSite() +
                            " IMAGEURL:" + anime.getOgImageUrl());
        }
    }
}
