package com.example.familyangel.popularmoviesapp;

/**
 * Created by FamilyAngel on 10/11/2016.
 */

/*class to contain data for a particular movie*/

public class movie {
    String originalName, releaseDate, movieOverview, posterLink;
    double userRating;

    //Constructor function for movie class.
    movie(String name, String link) {
        originalName = name;
        //releaseDate = date;
        //movieOverview = overview;
        posterLink = link;
        //userRating = rating;
    }

    movie(){
    }
}
