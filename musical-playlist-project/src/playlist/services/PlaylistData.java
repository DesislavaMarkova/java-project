package playlist.services;

import playlist.models.*;
import java.util.*;

/**
 * Клас за съхранение на текущите данни на приложението в паметта.
 */
public class PlaylistData {
    public List<Song> allSongs = new ArrayList<>();
    public List<Playlist> allPlaylists = new ArrayList<>();
    public List<PlaylistHistory> playbackHistory = new ArrayList<>();
    public String currentFileName;

    /**
     * Нулира всички списъци и текущото име на файл.
     */
    public void clear() {
        allSongs.clear();
        allPlaylists.clear();
        playbackHistory.clear();
        currentFileName = null;
    }
}