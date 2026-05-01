package playlist.services;

import playlist.models.*;
import java.util.List;

/**
 * Сервиз за управление на изпълнението и историята.
 */
public class PlaybackService {
    /**
     * Регистрира пускане на песен в историята.
     *
     * @param song Песен за изпълнение.
     * @param playlistName Име на плейлист.
     * @param history Списък с история.
     * @return Текст с информация за изпълнението.
     */
    public String playSong(Song song, String playlistName, List<PlaylistHistory> history) {
        if (song == null) return "Песента не е намерена";
        history.add(new PlaylistHistory(song, playlistName));
        return "Слушате: " + song.getArtist() + " " + song.getTitle();
    }
}