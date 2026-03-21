package models;

public class Song {
    private int  id;
    private String title;
    private String artist;
    private String album;
    private Genre genre;
    private String duration;
    private int year;

    public Song(int id, String title, String artist, String album, String duration, int year, Genre genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.year = year;
        this.genre = genre;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getDuration() { return duration; }
    public Genre getGenre() { return genre; }

    public void setTitle(String title) {
        this.title = title;
    }

    //извеждаме формата на песента
    @Override
    public String toString() {
        return "Song [ID=" + id + ", Title=" + title + ", Artist=" + artist + ", Genre=" + genre + "]";
    }
}
