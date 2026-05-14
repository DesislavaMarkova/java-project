package playlist.repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import playlist.models.Song;
import playlist.models.Playlist;
import playlist.models.PlaylistHistory;
import playlist.models.Genre;
import java.lang.reflect.Field;

/**
 * Клас за работа с файловата система.
 * Осигурява съхранение на данните чрез четене и запис в текстов формат.
 */
public class FileRepository {

    /**
     * Записва данните във файл, като използва метод за запис на низове.
     * Използва се символът \n за преминаване на нов ред във файла.
     *
     * @param path Път до файла.
     * @param songs Списък с песни.
     * @param playlists Списък с плейлисти.
     * @param history Списък с история.
     * @throws IOException При грешка при достъп до диска.
     */
    public void saveData(String path, List<Song> songs, List<Playlist> playlists,
                         List<PlaylistHistory> history) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            writer.write("---Songs---\n");
            for (Song s : songs) {
                String genreStr = s.getGenre() != null ? s.getGenre().name() : "NULL";
                String row = s.getId() + "|" + s.getTitle() + "|" + s.getArtist() + "|" +
                        s.getAlbum() + "|" + s.getDuration() + "|" + s.getYear() + "|" + genreStr + "\n";
                writer.write(row);
            }

            writer.write("---Playlists---\n");
            for (Playlist p : playlists) {
                writer.write(p.getName() + "|" + p.getDescription() + "\n");
                StringBuilder sb = new StringBuilder();
                for (Song s : p.getSongs()) {
                    sb.append(s.getId()).append(",");
                }
                String songList = sb.length() > 0 ? sb.toString() : "empty";
                writer.write(songList + "\n");
            }

            writer.write("---History---\n");
            for (PlaylistHistory h : history) {
                String pName = h.getPlaylistName() != null ? h.getPlaylistName() : "NULL";
                String row = h.getSong().getId() + "|" + pName + "|" + h.getPlaybackTime().toString() + "\n";
                writer.write(row);
            }
        }
    }

    /**
     * Зарежда данните от файл ред по ред.
     * Превръща текстовите редове обратно в Java обекти.
     *
     * @param path Път до файла.
     * @return Масив с обекти [песни, плейлисти, история].
     * @throws Exception При невалиден формат на данните.
     */
    public Object[] loadData(String path) throws Exception {
        List<Song> songs = new ArrayList<>();
        List<Playlist> playlists = new ArrayList<>();
        List<PlaylistHistory> history = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String currentSection = "";
            Playlist currentPlaylist = null;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (line.startsWith("---")) {
                    currentSection = line;
                    continue;
                }

                try {
                    if (currentSection.equals("---Songs---")) {
                        String[] parts = line.split("\\|");
                        int id = Integer.parseInt(parts[0]);
                        Genre genre = parts[6].equals("NULL") ? null : Genre.valueOf(parts[6]);
                        Song song = new Song(id, parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), genre);
                        songs.add(song);
                    }
                    else if (currentSection.equals("---Playlists---")) {
                        if (currentPlaylist == null) {
                            String[] parts = line.split("\\|");
                            currentPlaylist = new Playlist(parts[0], parts.length > 1 ? parts[1] : "");
                            playlists.add(currentPlaylist);
                        } else {
                            if (!line.equals("empty")) {
                                String[] songIds = line.split(",");
                                for (String sId : songIds) {
                                    if (sId.trim().isEmpty()) continue;
                                    int id = Integer.parseInt(sId.trim());
                                    for (Song s : songs) {
                                        if (s.getId() == id) {
                                            currentPlaylist.getSongs().add(s);
                                            break;
                                        }
                                    }
                                }
                            }
                            currentPlaylist = null;
                        }
                    }
                    else if (currentSection.equals("---History---")) {
                        String[] parts = line.split("\\|");
                        int songId = Integer.parseInt(parts[0]);
                        String pName = parts[1].equals("NULL") ? null : parts[1];
                        LocalDateTime time = LocalDateTime.parse(parts[2]);

                        Song found = null;
                        for (Song s : songs) {
                            if (s.getId() == songId) { found = s; break; }
                        }

                        if (found != null) {
                            PlaylistHistory h = new PlaylistHistory(found, pName);
                            Field timeField = PlaylistHistory.class.getDeclaredField("playbackTime");
                            timeField.setAccessible(true);
                            timeField.set(h, time);
                            history.add(h);
                        }
                    }
                } catch (Exception e) {}
            }
        }
        return new Object[]{songs, playlists, history};
    }
}