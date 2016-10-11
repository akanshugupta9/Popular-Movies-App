package com.example.familyangel.popularmoviesapp;

/**
 * Created by FamilyAngel on 10/11/2016.
 */

public class Movie {
    String originalName, releaseDate, movieOverview, posterLink;
    double userRating;

    //Constructor function for Movie class.
    Movie(String name, String link) {
        originalName = name;
        //releaseDate = date;
        //movieOverview = overview;
        posterLink = link;
        //userRating = rating;
    }
}
