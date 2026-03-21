package services;

import models.Genre;

public interface PlaylistServiceInterface{
    void open(String fileName);
    void close();
    void save();
    void saveAs(String path);

    String addSong(String title, String artist, String duration, Genre genre);
    String createPlaylist(String name, String description);
    void addSongToPlaylist(String playlistName, int songId, Integer position);
    void showPlaylist(String playlistName);

    void play(int songId, String playlistName);
    void showHistory(String fromDate, String toDate);

}
