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
     * Записва текущите промени в текущо отворения файл.
     */
    @Override
    public void save() {
        if (data.currentFileName != null) fileService.save(data, data.currentFileName);
    }

    /**
     * Записва текущото състояние на данните в нов файл.
     * @param path Път до новия файл.
     */
    @Override
    public void saveAs(String path) {fileService.save(data, path);}

    /**
     * Прекратява работата с текущите данни и освобождава паметта.
     */
    @Override
    public void close() {data.clear();}

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
     * Генерира класация на най-често слушаните песни.
     * @param n Брой записи в класацията.
     * @return Форматиран текст с топ песните.
     */
    @Override
    public String topTracks(int n) {
        return statsService.getTopTracks(data.playbackHistory, n);
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
     * Изчислява активността на всеки плейлист спрямо общия брой слушания.
     * @param from Начална дата (в този базов вариант се приема за информация).
     * @param to Крайна дата (в този базов вариант се приема за информация).
     * @param threshold Минимален процент активност.
     * @return Резултат от анализа.
     */
    @Override
    public String lowActivity(String from, String to, double threshold) {
        List<PlaylistHistory> history = data.playbackHistory;
        List<Playlist> playlists = data.allPlaylists;

        if (history.isEmpty()) {
            return "Историята е празна";
        }

        int totalPlays = history.size();
        StringBuilder sb = new StringBuilder("Плейлисти с активност под " + threshold + "%:\n");
        boolean found = false;

        for (Playlist p : playlists) {
            int count = 0;
            for (PlaylistHistory h : history) {
                if (h.getPlaylistName().equalsIgnoreCase(p.getName())) count++;
            }

            double percent = ((double) count / totalPlays) * 100;

            if (percent < threshold) {
                sb.append("- ").append(p.getName()).append(": ").append(String.format("%.2f", percent)).append("%\n");
                found = true;
            }
        }

        if (!found)
            return "Няма плейлисти с активност под зададения праг";

        sb.append("\nИзползвайте dropplaylist <име>, за да ги премахнете");
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
     * Генерира статистика за най-популярните изпълнители за определен период.
     * Претърсва историята и брои уникалните слушания за всеки изпълнител.
     *
     * @param n Колко изпълнители да покаже в класацията.
     * @param from Начална дата във формат (yyyy-MM-dd) за филтриране.
     * @param to Крайна дата във формат (yyyy-MM-dd) за филтриране.
     * @return Форматиран текст със списък от топ изпълнители и техния брой слушания.
     */
    @Override
    public String topArtists(int n, String from, String to) {
        if (data.playbackHistory == null || data.playbackHistory.isEmpty()) {
            return "Няма данни в историята";
        }

        Map<String, Integer> counts = new HashMap<>();

        for (PlaylistHistory h : data.playbackHistory) {
            String playbackDate = h.getPlaybackTime().toLocalDate().toString();

            if (from != null && playbackDate.compareTo(from) < 0) continue;
            if (to != null && playbackDate.compareTo(to) > 0) continue;

            if (h.getSong() != null) {
                String artist = h.getSong().getArtist();
                counts.put(artist, counts.getOrDefault(artist, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(counts.entrySet());
        list.sort((a, b)
                -> b.getValue().compareTo(a.getValue()));

        if (list.isEmpty()) return "Няма открити слушания за периода";

        StringBuilder sb = new StringBuilder("Топ " + n + " изпълнители\n");
        for (int i = 0; i < Math.min(n, list.size()); i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            sb.append((i + 1) + " " + entry.getKey() + " " + entry.getValue() + " слушания\n");
        }
        return sb.toString();
    }

    /** @return Списък с всички песни в системата. */
    public List<Song> getAllSongs() { return data.allSongs; }

    /** @return Списък с всички плейлисти. */
    public List<Playlist> getAllPlaylists() { return data.allPlaylists; }

    /** @return Пълната история на слушанията. */
    public List<PlaylistHistory> getHistory() { return data.playbackHistory; }

    /** @return Името на текущо заредения файл. */
    public String getCurrentFileName() { return data.currentFileName; }

    /** @return Статистика за плейлисти (в разработка). */
    @Override public String topPlaylists(int n) { return "Няма налични данни за плейлисти"; }
}