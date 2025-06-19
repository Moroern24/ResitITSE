package models;

public class Movie {
    private int movieID;
    private String title;
    private int releaseYear;
    private String director;
    private String genre;

    public Movie(int movieID, String title, int releaseYear, String director, String genre) {
        this.movieID = movieID;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.genre = genre;
    }

    public int getMovieID() { return movieID; }
    public String getTitle() { return title; }
    public int getReleaseYear() { return releaseYear; }
    public String getDirector() { return director; }
    public String getGenre() { return genre; }
}
