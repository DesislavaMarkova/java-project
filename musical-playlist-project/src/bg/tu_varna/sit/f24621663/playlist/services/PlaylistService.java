package services;

import exceptions.PlaylistException;
import models.Genre;
import models.Playlist;
import models.PlaylistHistory;
import models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Основен сервизен клас, който имплементира бизнес логиката на приложението.
 * Управлява колекциите от песни, плейлисти и история на слушанията.
 */
public class PlaylistService implements PlaylistServiceInterface {
    private List<Song> allSongs;
    private List<Playlist> allPlaylists;
    private List<PlaylistHistory> playbackHistory;
    private String currentFileName;

    public PlaylistService() {
        this.allSongs = new ArrayList<>();
        this.allPlaylists = new ArrayList<>();
        this.playbackHistory = new ArrayList<>();
    }

    @Override
    public String addSong(String title, String artist, String duration, Genre genre) {
        for (Song s : allSongs) {
            if (s.getTitle().equalsIgnoreCase(title) && s.getArtist().equalsIgnoreCase(artist)) {
                throw new PlaylistException("Песента '" + title + "' вече съществува!");
            }
        }

        int nextId = allSongs.size() + 1;
        Song newSong = new Song(nextId, title, artist, duration, "Unknown", 2024, genre);
        allSongs.add(newSong);

        StringBuilder sb = new StringBuilder();
        sb.append("Добавена песен: ").append(title).append(" [ID: ").append(nextId).append("]");
        return sb.toString();
    }

    @Override
    public String createPlaylist(String name, String description) {
        for (Playlist p : allPlaylists) {
            if (p.getName().equalsIgnoreCase(name)) {
                throw new PlaylistException("Плейлист с име '" + name + "' вече съществува!");
            }
        }

        Playlist playlist = new Playlist(name, description);
        allPlaylists.add(playlist);

        StringBuilder sb = new StringBuilder();
        sb.append("Създаден нов плейлист: ").append(name);
        return sb.toString();
    }

    @Override
    public void open(String fileName) {
        this.currentFileName = fileName;
    }

    @Override
    public void close() {
        allSongs.clear();
        allPlaylists.clear();
        playbackHistory.clear();
        currentFileName = null;
    }

    @Override
    public void save() {}

    @Override
    public void saveAs(String path) {}

    @Override
    public void addSongToPlaylist(String playlistName, int songId, Integer position) {}

    @Override
    public void showPlaylist(String playlistName) {}

    @Override
    public void play(int songId, String playlistName) {}

    @Override
    public void showHistory(String fromDate, String toDate) {}
}
