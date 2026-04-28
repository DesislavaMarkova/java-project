package playlist.models;

import java.io.Serializable;

/**
 * Представя музикална песен с нейните основни характеристики.
 */

public class Song implements Serializable {
    /**
     * Създава нов обект от тип Song.
     * @param id Уникален идентификатор на песента.
     * @param title Заглавие на песента.
     * @param artist Изпълнител на песента.
     * @param duration Продължителност във формат mm:ss.
     * @param album Име на албума.
     * @param year Година на издаване.
     * @param genre Жанр от тип {@link Genre}.
     */
    private int id;
    private String title;
    private String artist;
    private String album;
    private Genre genre;
    private String duration;
    private int year;

    public Song(int id, String title, String artist, String album, String duration, int year, Genre genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.year = year;
        this.genre = genre;
    }

    public int getId() { return id; }
    /** @return Заглавието на песента. */
    public String getTitle() { return title; }
    /** @return Изпълнителят на песента. */
    public String getArtist() { return artist; }
    public String getDuration() { return duration; }
    public Genre getGenre() { return genre; }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Song [ID=" + id + ", Title=" + title + ", Artist=" + artist + ", Genre=" + genre + "]";
    }
}
