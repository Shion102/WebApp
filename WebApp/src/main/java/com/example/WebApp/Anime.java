package com.example.WebApp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
class Anime {
    private int id;
    private String title;
    private String media;
    private String officialSite;
    private String ogImageUrl;
    private String season;
}