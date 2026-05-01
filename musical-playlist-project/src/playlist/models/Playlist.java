package playlist.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Представя музикален плейлист, който съдържа колекция от песни.
 */

public class Playlist implements Serializable {
    /**
     * Инициализира нов плейлист с име и описание.
     * @param name Уникално име на плейлиста.
     * @param description Кратко описание на съдържанието.
     */
    private String name;
    private String description;
    private List<Song> songs;

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
        this.songs = new ArrayList<>();
    }

    /**
     * Добавя песен в края на плейлиста.
     * @param song Обект от тип {@link Song}.
     */
    public void addSong(Song song) {this.songs.add(song);}

    public void addSongAt(int position, Song song) {
        if(position >= 0 && position <= songs.size()) this.songs.add(position, song);
    }

    public void removeSong(int songId){}
    public void getTotalDuration(){}

    /** @return Списък с всички песни в плейлиста. */
    public List<Song> getSongs() {return songs;}
    public String getName() {return name;}
    public String getDescription() {return description;}

}
