package models;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private String description;
    private List<Song> songs;

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void addSongAt(int position, Song song) {
        if(position >= 0 && position <= songs.size()) { this.songs.add(position, song); }
    }

    public void removeSong(int songId){}
    public void getTotalDuration(){}

    public List<Song> getSongs() {return songs;}
    public String getName() { return name; }
}
