package playlist.services;

import playlist.models.*;
import java.util.*;

/**
 * Централен координатор на услугите в музикалното приложение.
 * Този клас имплементира основния интерфейс и пренасочва логиката към специализирани под-сервизи.
 */
public class PlaylistService implements PlaylistServiceInterface {
    private final PlaylistData data = new PlaylistData();
    private final FileService fileService = new FileService();
    private final MusicService musicService = new MusicService();
    private final PlaybackService playbackService = new PlaybackService();
    private final StatisticsService statsService = new StatisticsService();

    /**
     * Зарежда данни от външен файл в паметта на приложението.
     * @param fileName Име на файла за зареждане.
     */
    @Override
    public void open(String fileName) {fileService.load(fileName, data);}

    /**
     * Записва текущото състояние на данните в отворения файл.
     *
     * @return Съобщение за успех или информация, че няма отворен файл.
     */
    @Override
    public String save() {
        if (data.currentFileName != null) {
            fileService.save(data, data.currentFileName);
            return "Промените са записани успешно в " + data.currentFileName;
        }
        return "Няма отворен файл за запис";
    }

    /**
     * Прекратява работата с текущите данни и освобождава паметта.
     *
     * @return Съобщение за успешно затваряне или липса на отворен файл.
     */
    @Override
    public String close() {
        if (data.currentFileName != null) {
            data.allSongs.clear();
            data.allPlaylists.clear();
            data.playbackHistory.clear();
            data.currentFileName = null;
            return "Файлът е затворен и паметта е изчистена";
        }
        return "Няма отворен файл за затваряне";
    }

    /**
     * Записва текущото състояние на данните в нов файл.
     * @param path Път до новия файл.
     */
    @Override
    public void saveAs(String path) {fileService.save(data, path);}


    /**
     * Създава и добавя нова песен към глобалния списък.
     * @param title Заглавие на песента.
     * @param artist Изпълнител.
     * @param duration Продължителност (mm:ss).
     * @param genre Музикален жанр.
     * @return Текст с резултата от операцията.
     */
    @Override
    public String addSong(String title, String artist, String duration, Genre genre) {
        return musicService.addSong(data.allSongs, title, artist, duration, genre);
    }

    /**
     * Стартира изпълнение на песен и я записва в историята.
     * @param songId Уникален идентификатор на песента.
     * @param playlistName Име на плейлиста, от който се пуска.
     * @return Съобщение за текущото изпълнение.
     */
    @Override
    public String play(int songId, String playlistName) {
        Song s = musicService.findSongById(data.allSongs, songId);
        return playbackService.playSong(s, playlistName, data.playbackHistory);
    }

    /**
     * Инициализира нов празен плейлист в системата.
     * @param name Име на плейлиста.
     * @param description Кратко описание.
     * @return Потвърждение за създаване.
     */
    @Override
    public String createPlaylist(String name, String description) {
        data.allPlaylists.add(new Playlist(name, description));
        return "Плейлистът '" + name + "' е създаден успешно";
    }

    /**
     * Добавя съществуваща песен към конкретен плейлист.
     * @param playlistName Име на целевия плейлист.
     * @param songId ID на песента за добавяне.
     * @param position Позиция в списъка (може да бъде null).
     */
    @Override
    public void addSongToPlaylist(String playlistName, int songId, Integer position) {
        Playlist p = musicService.findPlaylistByName(data.allPlaylists, playlistName);
        Song s = musicService.findSongById(data.allSongs, songId);

        if (p != null && s != null) p.getSongs().add(s);
    }


    /**
     * Извежда списък с всички песни в даден плейлист.
     * @param name Име на плейлиста.
     * @return Списък с песни или съобщение за грешка.
     */
    @Override
    public String showPlaylist(String name) {
        Playlist p = musicService.findPlaylistByName(data.allPlaylists, name);
        if (p == null) return "Плейлистът не е намерен";

        StringBuilder sb = new StringBuilder("Съдържание на '" + name + "':\n");
        for (Song s : p.getSongs()) {
            sb.append(" ").append(s.getArtist()).append(" ").append(s.getTitle()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Показва хронологичен списък на последно слушаните песни.
     * @param fromDate Начална дата (опционално).
     * @param toDate Крайна дата (опционално).
     * @return Форматирана история.
     */
    @Override
    public String showHistory(String fromDate, String toDate) {
        if (data.playbackHistory.isEmpty()) return "Историята е празна";

        StringBuilder sb = new StringBuilder("Пълна история на слушанията:\n");
        for (PlaylistHistory h : data.playbackHistory) {
            sb.append(" ").append(h.getSong().getArtist())
                    .append(" ").append(h.getSong().getTitle()).append("\n");
        }
        return sb.toString();
    }


    /**
     * Премахва плейлист от системата по име.
     * @param name Име на плейлиста.
     * @return Статус съобщение.
     */
    @Override
    public String dropPlaylist(String name) {
        for (int i = 0; i < data.allPlaylists.size(); i++) {
            if (data.allPlaylists.get(i).getName().equalsIgnoreCase(name)) {
                data.allPlaylists.remove(i);
                return "Плейлистът '" + name + "' беше успешно премахнат";
            }
        }
        return "Плейлистът не е намерен";
    }

    /**
     * Генерира форматиран текст със списък от песни според филтри.
     */
    @Override
    public String listSongs(String artist, String genre, Integer year) {
        int yearVal = (year == null) ? 0 : year;
        List<Song> filtered = musicService.filterSongs(data.allSongs, artist, genre, yearVal);

        if (filtered.isEmpty()) return "Няма намерени песни по тези критерии.";


        StringBuilder sb = new StringBuilder("Списък с песни:\n");
        for (Song s : filtered) {
            sb.append(String.format("[%d] %s - %s (%s, %d)\n",
                    s.getId(), s.getArtist(), s.getTitle(), s.getGenre(), s.getYear()));
        }
        return sb.toString();
    }

    /**
     * Премества песен в рамките на плейлист.
     * @param playlistName Име на плейлиста.
     * @param fromPos Начална позиция (1-базирана).
     * @param toPos Целева позиция (1-базирана).
     * @return Текст с потвърждение.
     */
    @Override
    public String moveSong(String playlistName, int fromPos, int toPos) {
        Playlist p = musicService.findPlaylistByName(data.allPlaylists, playlistName);
        if (p == null) return "Грешка: Плейлистът не е намерен";

        return musicService.moveSongInPlaylist(p, fromPos, toPos);
    }

    /**
     * Изпълнява операцията по разбъркване чрез намиране на съответния плейлист.
     * @param playlistName Име на плейлиста за разбъркване.
     * @param seed Параметър за случайност.
     * @return Резултат от операцията под формата на текст.
     */
    @Override
    public String shuffle(String playlistName, Long seed) {
        Playlist p = musicService.findPlaylistByName(data.allPlaylists, playlistName);
        if (p == null) return "Плейлистът не е намерен.";

        musicService.shufflePlaylist(p, seed);
        return "Плейлистът '" + playlistName + "' беше разбъркан успешно";
    }

    /**
     * Премахва песен от глобалния списък и от всички съществуващи плейлисти.
     * Методът използва музикалния сервиз, за да гарантира, че след изтриването
     * на песента няма да останат невалидни препратки към нея в системата.
     *
     * @param songId Уникалният идентификатор на песента, която трябва да бъде премахната.
     * @return Текстово съобщение за успешно изтриване или съобщение за грешка,
     *         ако песен с такова ID не е открита.
     */
    @Override
    public String removeSong(int songId) {
        boolean success = musicService.removeSongEverywhere(data.allSongs, data.allPlaylists, songId);
        if (success) return "Песента с ID " + songId + " беше успешно премахната от системата";
        return "Песен с такова ID не съществува";
    }

    /**
     * Търси песен по ID и връща пълната ѝ информация.
     * Използва се за детайлен преглед на метаданните на песента.
     *
     * @param songId ID на търсената песен.
     * @return Текст с информация или съобщение, че песента не съществува.
     */
    @Override
    public String songInfo(int songId) {
        for (Song s : data.allSongs) {
            if (s.getId() == songId) {
                return "Информация за песен\n" +
                        "ID: " + s.getId() + "\n" +
                        "Заглавие: " + s.getTitle() + "\n" +
                        "Изпълнител: " + s.getArtist() + "\n" +
                        "Албум: " + s.getAlbum() + "\n" +
                        "Година: " + s.getYear() + "\n" +
                        "Жанр: " + s.getGenre() + "\n" +
                        "Продължителност: " + s.getDuration();
            }
        }
        return "Песен с ID " + songId + " не е намерена";
    }


    /**
     * Връща списък с всички песни, регистрирани в системата.
     *
     * @return Списък от обекти тип Song.
     */
    public List<Song> getAllSongs() { return data.allSongs; }

    /**
     * Връща списък с всички създадени плейлисти.
     *
     * @return Списък от обекти тип Playlist.
     */
    public List<Playlist> getAllPlaylists() { return data.allPlaylists; }

    /**
     * Предоставя пълната история на слушанията до момента.
     *
     * @return Списък от записи в историята.
     */
    public List<PlaylistHistory> getHistory() { return data.playbackHistory; }

    /**
     * Връща името на файла, който е зареден в паметта.
     *
     * @return Името на файла или null, ако няма отворен файл.
     */
    public String getCurrentFileName() { return data.currentFileName; }

    /**
     * Показва класация на най-активните плейлисти на база броя слушания в тях.
     *
     * @param n Брой плейлисти за показване в класацията.
     * @return Форматиран текст с имената на плейлистите и броя им слушания.
     */
    @Override
    public String topPlaylists(int n) {
        return statsService.getTopPlaylists(data.playbackHistory, n);
    }
    /**
     * Генерира класация на най-често слушаните песни за период.
     * @param n Брой записи в класацията.
     * @return Форматиран текст с топ песните.
     */
    @Override
    public String topTracks(int n) {
        return statsService.getTopTracks(data.playbackHistory, n, null, null);
    }

    /**
     * Генерира статистика за най-популярните изпълнители за определен период.
     * @param n Колко изпълнители да покаже в класацията.
     * @param from Начална дата за филтриране.
     * @param to Крайна дата за филтриране.
     * @return Форматиран текст със списък от топ изпълнители.
     */
    @Override
    public String topArtists(int n, String from, String to) {
        return statsService.getTopArtists(data.playbackHistory, n, from, to);
    }

    /**
     * Изчислява активността на всеки плейлист спрямо общия брой слушания.
     * @param from Начална дата.
     * @param to Крайна дата.
     * @param threshold Минимален процент активност.
     * @return Резултат от анализа.
     */
    @Override
    public String lowActivity(String from, String to, double threshold) {
        return statsService.getLowActivityPlaylists(data.allPlaylists, data.playbackHistory, threshold);
    }
}