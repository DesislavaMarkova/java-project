package services;

import models.Genre;

/**
 * Интерфейс, дефиниращ задължителните операции за управление на музикален плейлист.
 */
public interface PlaylistServiceInterface{
    /**
     * Добавя нова песен в глобалния списък.
     * @throws PlaylistException ако песента вече съществува.
     * @return Съобщение за успешно добавяне.
     */
    void open(String fileName);
    void close();
    void save();
    void saveAs(String path);

    String addSong(String title, String artist, String duration, Genre genre);
    /**
     * Създава нов празен плейлист.
     * @param name Име на плейлиста.
     * @param description Описание.
     * @return Статус на операцията.
     */
    String createPlaylist(String name, String description);
    void addSongToPlaylist(String playlistName, int songId, Integer position);
    void showPlaylist(String playlistName);

    void play(int songId, String playlistName);
    void showHistory(String fromDate, String toDate);

}
