package models;

public class ViewingHabit {
    private int userID;
    private int movieID;
    private int minutesWatched;

    public ViewingHabit(int userID, int movieID, int minutesWatched) {
        this.userID = userID;
        this.movieID = movieID;
        this.minutesWatched = minutesWatched;
    }

    public int getUserID() { return userID; }
    public int getMovieID() { return movieID; }
    public int getMinutesWatched() { return minutesWatched; }
}
