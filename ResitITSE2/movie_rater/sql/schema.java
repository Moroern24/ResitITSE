DROP TABLE IF EXISTS ViewingHabit;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Movie;

CREATE TABLE User (
        UserID INTEGER PRIMARY KEY AUTOINCREMENT,
        Age INTEGER,
        Email TEXT
);

CREATE TABLE Movie (
        MovieID INTEGER PRIMARY KEY AUTOINCREMENT,
        Title TEXT,
        ReleaseYear INTEGER,
        Director TEXT,
        Genre TEXT
);

CREATE TABLE ViewingHabit (
        UserID INTEGER,
        MovieID INTEGER,
        MinutesWatched INTEGER,
        FOREIGN KEY (UserID) REFERENCES User(UserID),
FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
        );
