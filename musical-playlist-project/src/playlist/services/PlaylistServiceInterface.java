package playlist.services;

import playlist.models.Genre;

/**
 * Интерфейс, дефиниращ задължителните операции за управление на музикална система.
 * Осигурява методи за работа с файлове, управление на песни, плейлисти и статистики.
 */
public interface PlaylistServiceInterface {

    /**
     * Зарежда данни от външен файл в паметта.
     * @param fileName Име или път до файла.
     */
    void open(String fileName);

    /**
     * Изчиства всички заредени данни и затваря текущата сесия.
     */
    void close();

    /**
     * Записва текущото състояние на данните в текущо отворения файл.
     */
    void save();

    /**
     * Записва текущите данни в нов файл на посочения път.
     * @param path Път до новия файл.
     */
    void saveAs(String path);

    /**
     * Добавя нова песен към глобалния списък.
     * @param title Заглавие на песента.
     * @param artist Изпълнител.
     * @param duration Продължителност (mm:ss).
     * @param genre Музикален жанр.
     * @return Текст с резултата от операцията.
     */
    String addSong(String title, String artist, String duration, Genre genre);

    /**
     * Създава нов празен плейлист в системата.
     * @param name Име на плейлиста.
     * @param description Кратко описание.
     * @return Статус на операцията.
     */
    String createPlaylist(String name, String description);

    /**
     * Добавя съществуваща песен към конкретен плейлист.
     * @param playlistName Име на целевия плейлист.
     * @param songId Идентификатор на песента.
     * @param position Позиция в списъка (може да бъде null).
     */
    void addSongToPlaylist(String playlistName, int songId, Integer position);

    /**
     * Извежда списък с песните в конкретен плейлист.
     * @param playlistName Име на плейлиста.
     * @return Форматиран списък с песни.
     */
    String showPlaylist(String playlistName);

    /**
     * Регистрира слушане на песен и я добавя в историята.
     * @param songId ID на песента.
     * @param playlistName Име на плейлиста, от който е пусната.
     * @return Информация за изпълнението.
     */
    String play(int songId, String playlistName);

    /**
     * Показва историята на слушанията в даден времеви период.
     * @param fromDate Начална дата.
     * @param toDate Крайна дата.
     * @return Форматирана история.
     */
    String showHistory(String fromDate, String toDate);

    /**
     * Генерира класация на най-слушаните песни.
     * @param n Брой песни за показване.
     * @return Статистика за топ песни.
     */
    String topTracks(int n);

    /**
     * Генерира класация на най-активните плейлисти.
     * @param n Брой плейлисти за показване.
     * @return Статистика за топ плейлисти.
     */
    String topPlaylists(int n);

    /**
     * Премахва плейлист от системата.
     * @param name Име на плейлиста.
     * @return Съобщение за резултата.
     */
    String dropPlaylist(String name);

    /**
     * Анализира активността на плейлистите за период и връща тези под прага.
     * @param from Начална дата.
     * @param to Крайна дата.
     * @param threshold Процентен праг.
     * @return Списък със слабо активни плейлисти.
     */
    String lowActivity(String from, String to, double threshold);

    String listSongs(String artist, String genre, Integer year);

    String moveSong(String playlistName, int fromPos, int toPos);

    /**
     * Разбърква песните в конкретен плейлист.
     * Поддържа опционален параметър seed за повтаряемост на резултата.
     * @param playlistName Име на плейлиста.
     * @param seed Стойност за генериране на случайност (може да бъде null).
     * @return Статус съобщение за резултата от операцията.
     */
    String shuffle(String playlistName, Long seed);

    /**
     * Премахва песен от системата по идентификатор.
     * @param songId ID на песента.
     * @return Статус съобщение.
     */
    String removeSong(int songId);

    /**
     * Показва подробна информация за песен по нейното ID.
     * @param songId Идентификатор на песента.
     * @return Форматиран текст с детайлите на песента.
     */
    String songInfo(int songId);

    /**
     * Изчислява кои са най-слушаните изпълнители за определен период.
     * @param n Брой изпълнители в класацията.
     * @param from Начална дата (може да е null).
     * @param to Крайна дата (може да е null).
     * @return Форматиран списък с топ изпълнители.
     */
    String topArtists(int n, String from, String to);
}