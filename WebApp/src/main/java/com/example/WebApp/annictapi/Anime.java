package com.example.WebApp.annictapi;

import lombok.Data;

@Data
public class Anime {
    private int id;
    private String title;
    private String media;
    private String officialSite;
    private String ogImageUrl;
    private String epCount;
    private String season;
}