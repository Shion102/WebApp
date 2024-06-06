package com.example.WebApp;

import lombok.Data;

@Data
public class AniLogReview {
    private int animeId;
    private int rate;
    private String comment;
    private int userId;
}
