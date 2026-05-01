package playlist.models;

import java.io.Serializable;

/**
 * Представя музикална песен с нейните основни характеристики.
 */
public class Song implements Serializable {
    private int id;
    private String title;
    private String artist;
    private String album;
    private Genre genre;
    private String duration;
    private int year;

    /**
     * Конструктор за създаване на нов обект от тип Song.
     *
     * @param id Уникален идентификатор.
     * @param title Заглавие.
     * @param artist Изпълнител.
     * @param album Албум.
     * @param duration Продължителност (mm:ss).
     * @param year Година на издаване.
     * @param genre Жанр от тип {@link Genre}.
     */
    public Song(int id, String title, String artist, String album, String duration, int year, Genre genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.year = year;
        this.genre = genre;
    }

    /** @return Уникалното ID на песента. */
    public int getId() { return id; }

    /** @return Заглавието на песента. */
    public String getTitle() { return title; }

    /** @return Изпълнителят на песента. */
    public String getArtist() { return artist; }

    /** @return Продължителността на песента. */
    public String getDuration() { return duration; }

    /** @return Жанрът на песента. */
    public Genre getGenre() { return genre; }

    /** @param title Ново заглавие на песента. */
    public void setTitle(String title) {this.title = title;}

    /** @return Името на албума. */
    public String getAlbum() { return album; }

    /** @return Годината на издаване. */
    public int getYear() { return year; }

    @Override
    public String toString() {
        return "Song [ID=" + id + ", Title=" + title + ", Artist=" + artist +
                ", Album=" + album + ", Year=" + year + ", Genre=" + genre + "]";
    }
}