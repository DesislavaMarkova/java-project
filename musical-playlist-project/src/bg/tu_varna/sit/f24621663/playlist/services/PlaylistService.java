package services;

import exceptions.PlaylistException;
import models.Genre;
import models.Playlist;
import models.PlaylistHistory;
import models.Song;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.util.Map;
import java.util.Collections;

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
    @SuppressWarnings("uncheked")
    public void open(String fileName) {
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            allSongs = (List<Song>) ois.readObject();
            allPlaylists = (List<Playlist>) ois.readObject();
            playbackHistory = (List<PlaylistHistory>) ois.readObject();
            ois.close();
            this.currentFileName = fileName;
        }
        catch (Exception e) {
            throw new PlaylistException("Файлът не може да бъде отворен");
        }
    }

    @Override
    public void close() {
        allSongs.clear();
        allPlaylists.clear();
        playbackHistory.clear();
        currentFileName = null;
    }

    @Override
    public void save() {
        if (this.currentFileName != null) saveAs(this.currentFileName);
        else throw new PlaylistException("Запишете файла 'save as' ");
    }

    @Override
    public void saveAs(String path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(allSongs);
            oos.writeObject(allPlaylists);
            oos.writeObject(playbackHistory);
            oos.close();
            this.currentFileName = path;
        }
        catch (IOException e) {
            throw new PlaylistException("Грешка при записването на файла");
        }
    }

    @Override
    public void addSongToPlaylist(String playlistName, int songId, Integer position) {}

    @Override
    public String showPlaylist(String playlistName) {
        Playlist playlist = null;
        for (Playlist p : allPlaylists) {
            if (p.getName().equalsIgnoreCase(playlistName)) {
                playlist = p;
                break;
            }
        }
        if (playlist == null) {
            throw new PlaylistException("Плейлист с име '" + playlistName +
                    "'не беше намерен.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Плейлист: ").append(playlist.getName()).append("\n");
        sb.append("Описание: ").append(playlist.getDescription()).append("\n");

        int totalSeconds = 0;
        List<Song> songs = playlist.getSongs();

        for(int i=0; i<songs.size(); i++) {
            Song song = songs.get(i);
            sb.append(i + 1).append(". ").append(song.getTitle()).append("-").append(song.getDuration()).append("\n");

            String[] parts = song.getDuration().split(":");
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            totalSeconds += (minutes * 60) + seconds;
        }

        int finalMinutes = totalSeconds / 60;
        int finalSeconds = totalSeconds % 60;

        sb.append("Продължителност на песента: ")
                .append(String.format("%02d:%02d"), finalMinutes, finalSeconds);

        return sb.toString();
    }

    @Override
    public String play(int songId, String playlistName) {
        Song songToPlay = null;
        for (Song s : allSongs) {
            if (s.getId() == songId) {
                songToPlay = s;
                break;
            }
        }
        if (songToPlay == null) {
            throw new PlaylistException("Не е намерена песен с ID: " + songId);
        }

        PlaylistHistory historyEntry = new PlaylistHistory(songToPlay, playlistName);
        playbackHistory.add(historyEntry);

        StringBuilder sb = new StringBuilder();
        sb.append("Пускане на: ").append(songToPlay.getTitle())
                .append("от").append(songToPlay.getArtist());

        if (playlistName != null && !playlistName.isEmpty()) {
            sb.append("\nИзточник (плейлист): ").append(playlistName);
        }

        return sb.toString();
    }

    @Override
    public String showHistory(String fromDate, String toDate) {
        LocalDate start = LocalDate.parse(fromDate);
        LocalDate end = LocalDate.parse(toDate);

        StringBuilder sb = new StringBuilder();
        sb.append("История на слушателя от ").append(fromDate)
                .append("до").append(toDate).append("\n");

        boolean found = false;

        for (PlaylistHistory history : playbackHistory) {
            LocalDate historyDate = history.getPlaybackTime().toLocalDate();

            if((historyDate.isAfter(start) || historyDate.equals(start))
            && (historyDate.isBefore(end) || historyDate.equals(end))) {

                sb.append("[").append(history.getPlaybackTime().toString()
                        .replace("Т", " ")).append("]")
                        .append(history.getSong().getArtist()).append("-")
                        .append(history.getSong().getTitle());

                if(history.getPlaylistName() != null) {
                    sb.append(" (от плейлист: ").append(history.getPlaylistName()).append(")");
                }

                sb.append("\n");
                found = true;
            }
        }

        if (!found) {
            return "Няма намерени записи от този период";
        }
        return sb.toString();
    }

    @Override
    public String topTracks(int n) {
        if (playbackHistory.isEmpty()) {
            return "Все още няма данни за историята";
        }

        Map<Song, Integer> counts = new HashMap<>();
        for (PlaylistHistory record : playbackHistory) {
            Song s = record.getSong();
            counts.put(s, counts.getOrDefault(s, 0) + 1);
        }

        List<Map.Entry<Song, Integer>> sortedList = new ArrayList<>(counts.entrySet());
        sortedList.sort((e1, e2)
                -> e2.getValue().compareTo(e1.getValue()));

        StringBuilder sb = new StringBuilder();
        sb.append("топ").append(n).append("най-слушани песни\n");

        int limit = Math.min(n, sortedList.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<Song, Integer> entry = sortedList.get(i);
            sb.append(i + 1).append(".")
                    .append(entry.getKey().getArtist()).append(" - ")
                    .append(entry.getKey().getTitle())
                    .append(" (слушана ").append(entry.getValue()).append(" пъти)\n");
        }

        return sb.toString();
    }

    @Override
    public String topPlaylists(int n) {
        if (playbackHistory.isEmpty()) {
            return "Няма записи в историята";
        }

        List<String> names = new ArrayList<>();
        for (PlaylistHistory history : playbackHistory) {
            if(history.getPlaylistName() != null
            && !history.getPlaylistName().isEmpty()) {
                names.add(history.getPlaylistName());
            }
        }

        if(names.isEmpty()) {
            return "Няма слушани песни от конкретните плейлисти";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Топ плейлисти\n");

        java.util.Collections.sort(names);

        String currentName = names.get(0);
        int count = 0;
        int playlistsShown = 0;

        for(int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(currentName)) {
                count++;
            }
            else{
                sb.append(currentName).append("-")
                        .append(count).append("слушания\n");
                playlistsShown++;

                if(playlistsShown >= n) break;

                currentName = names.get(i);
                count = 1;
            }
        }

        if(playlistsShown < n){
            sb.append(currentName).append("-").append(count)
                    .append("слушания\n");
        }
        return sb.toString();
    }
}
