package models;

public class Song {
    private int  id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String duration;
    private int year;

    public Song(int id, String title, String artist, String album, String genre, String duration, int year) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
        this.year = year;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getDuration() { return duration; }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s - %s [%s] (%s)", id, artist, title, album, duration);
    }
}
