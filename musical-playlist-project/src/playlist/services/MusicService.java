package playlist.services;

import playlist.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Сервиз за управление на логиката за песни и плейлисти.
 */
public class MusicService {

    /**
     * Търси песен по нейния идентификатор.
     *
     * @param songs Списък с песни.
     * @param id Търсено ID.
     * @return Намерената песен или null.
     */
    public Song findSongById(List<Song> songs, int id) {
        for (Song s : songs) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    /**
     * Търси плейлист по неговото име.
     *
     * @param playlists Списък с плейлисти.
     * @param name Име на плейлиста.
     * @return Намереният плейлист или null.
     */
    public Playlist findPlaylistByName(List<Playlist> playlists, String name) {
        for (Playlist p : playlists) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    /**
     * Добавя нова песен в списъка, ако не съществува.
     *
     * @param songs Текущ списък с песни.
     * @param title Заглавие.
     * @param artist Изпълнител.
     * @param duration Продължителност.
     * @param genre Жанр.
     * @return Статус на операцията.
     */
    public String addSong(List<Song> songs, String title, String artist,
                          String duration, Genre genre) {
        for (Song s : songs) {
            if (s.getTitle().equalsIgnoreCase(title) && s.getArtist().equalsIgnoreCase(artist))
                return "Песента вече съществува!";
        }
        songs.add(new Song(songs.size() + 1, title, artist, "Unknown",
                duration, 2024, genre));
        return "Успешно добавена: " + title;
    }

    /**
     * Филтрира песни по дадени критерии.
     * @param allSongs Списък с всички налични песни.
     * @param artist Търсен изпълнител (null, ако не се филтрира).
     * @param genre Търсен жанр (null, ако не се филтрира).
     * @param year Търсена година (0, ако не се филтрира).
     * @return Списък с филтрираните песни.
     */
    public List<Song> filterSongs(List<Song> allSongs, String artist, String genre, int year) {
        List<Song> result = new ArrayList<>();

        for (Song s : allSongs) {
            boolean matchesArtist = (artist == null || s.getArtist().equalsIgnoreCase(artist));
            boolean matchesGenre = (genre == null || (s.getGenre() != null
                    && s.getGenre().toString().equalsIgnoreCase(genre)));
            boolean matchesYear = (year == 0 || s.getYear() == year);

            if (matchesArtist && matchesGenre && matchesYear) result.add(s);
        }
        return result;
    }

    /**
     * Премества песен в рамките на плейлист от една позиция на друга.
     * @param playlist Обектът плейлист, в който се прави промяната.
     * @param fromPos Начална позиция (1-базирана).
     * @param toPos Целева позиция (1-базирана).
     * @return Текст с резултата от операцията.
     */
    public String moveSongInPlaylist(Playlist playlist, int fromPos, int toPos) {
        List<Song> songs = playlist.getSongs();

        if (fromPos < 1 || fromPos > songs.size() || toPos < 1 || toPos > songs.size())
            return "Невалидни позиции" + "Плейлистът има " + songs.size() + " песни.";

        int fromIndex = fromPos - 1;
        int toIndex = toPos - 1;

        Song songToMove = songs.remove(fromIndex);
        songs.add(toIndex, songToMove);

        return "Песента '" + songToMove.getTitle() + "' беше преместена на позиция " + toPos;
    }

    /**
     * Разбърква песните в плейлиста.
     * @param playlist Обектът плейлист.
     * @param seed Число за случаен избор (може да е null).
     */
    public void shufflePlaylist(Playlist playlist, Long seed) {
        if (seed != null) Collections.shuffle(playlist.getSongs(), new Random(seed));
        else Collections.shuffle(playlist.getSongs());
    }

    /**
     * Изтрива песен от глобалния списък и от всички плейлисти.
     * @param allSongs Глобален списък с песни.
     * @param allPlaylists Списък с всички плейлисти.
     * @param songId ID на песента за изтриване.
     * @return true ако песента е намерена и изтрита, иначе false.
     */
    public boolean removeSongEverywhere(List<Song> allSongs, List<Playlist> allPlaylists, int songId) {
        boolean removed = allSongs.removeIf(s -> s.getId() == songId);

        if (removed) {
            for (Playlist p : allPlaylists) {
                p.getSongs().removeIf(s -> s.getId() == songId);
            }
        }
        return removed;
    }
}