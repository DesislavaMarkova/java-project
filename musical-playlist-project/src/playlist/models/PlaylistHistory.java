package playlist.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Модел, представящ запис в историята на слушанията.
 * Съдържа информация за песента, времето на изпълнение и името на плейлиста.
 */
public class PlaylistHistory implements Serializable {
    /** Песента, която е била пусната. */
    private Song song;
    /** Точната дата и час на пускане на песента. */
    private LocalDateTime playbackTime;
    /** Име на плейлиста, от който е пусната песента (може да бъде null). */
    private String playlistName;

    /**
     * Конструктор за създаване на нов запис в историята.
     * Автоматично задава текущото време като време на слушане.
     *
     * @param song Песента, която се добавя в хронологията.
     * @param playlistName Името на плейлиста, от който е стартирана песента.
     */
    public PlaylistHistory(Song song, String playlistName) {
        this.song = song;
        this.playbackTime = LocalDateTime.now();
        this.playlistName = playlistName;
    }

    /** @return Обектът на песента. */
    public Song getSong() {return song;}

    /** @return Датата и часа на слушане. */
    public LocalDateTime getPlaybackTime() {return playbackTime;}

    /** @return Името на плейлиста. */
    public String getPlaylistName() {return playlistName;}
}