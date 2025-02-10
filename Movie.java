package org.example;

import java.util.List;

public class Movie {
    private String movieId;
    private String title;
    private int releaseYear;
    private String genre;
    private double rating;
    private int duration;
    private String directorId;
    private List<String> actorIds;

    public Movie(String movieId, String title, int releaseYear, String genre, double rating, int duration, String directorId, List<String> actorIds) {
        this.movieId = movieId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
        this.duration = duration;
        this.directorId = directorId;
        this.actorIds = actorIds;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public int getDuration() {
        return duration;
    }

    public String getDirectorId() {
        return directorId;
    }

    public List<String> getActorIds() {
        return actorIds;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId='" + movieId + '\'' +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", duration=" + duration +
                ", directorId='" + directorId + '\'' +
                ", actorIds=" + actorIds +
                '}';
    }
}
