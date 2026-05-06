package playlist.services;

import playlist.models.*;
import java.util.*;

/**
 * Сервиз за генериране на статистики и анализи за слушанията.
 * Изчислява популярност на песни и активност на плейлистите.
 */
public class StatisticsService {

    /**
     * Изчислява най-слушаните песни за определен период.
     *
     * @param history Списък с историята на слушанията.
     * @param n Брой резултати за показване.
     * @param from Начална дата (yyyy-MM-dd).
     * @param to Крайна дата (yyyy-MM-dd).
     * @return Форматиран низ с класацията.
     */
    public String getTopTracks(List<PlaylistHistory> history, int n, String from, String to) {
        if (history.isEmpty()) return "Няма данни за слушания";

        Map<String, Integer> counts = new HashMap<>();

        for (PlaylistHistory record : history) {
            String playbackDate = record.getPlaybackTime().toLocalDate().toString();

            if (from != null && playbackDate.compareTo(from) < 0) continue;
            if (to != null && playbackDate.compareTo(to) > 0) continue;

            String title = record.getSong().getTitle();
            counts.put(title, counts.getOrDefault(title, 0) + 1);
        }
        return formatRanking(counts, n, "най-слушани песни");
    }

    /**
     * Изчислява най-популярните изпълнители за определен период.
     *
     * @param history Списък с историята на слушанията.
     * @param n Брой резултати за показване.
     * @param from Начална дата (yyyy-MM-dd).
     * @param to Крайна дата (yyyy-MM-dd).
     * @return Форматиран низ с класацията.
     */
    public String getTopArtists(List<PlaylistHistory> history, int n, String from, String to) {
        if (history.isEmpty())  return "Няма данни в историята";

        Map<String, Integer> counts = new HashMap<>();

        for (PlaylistHistory record : history) {
            String playbackDate = record.getPlaybackTime().toLocalDate().toString();

            if (from != null && playbackDate.compareTo(from) < 0) continue;
            if (to != null && playbackDate.compareTo(to) > 0) continue;

            if (record.getSong() != null) {
                String artist = record.getSong().getArtist();
                counts.put(artist, counts.getOrDefault(artist, 0) + 1);
            }
        }
        return formatRanking(counts, n, "топ изпълнители");
    }

    /**
     * Помощен метод за сортиране и форматиране на резултатите в списък.
     *
     * @param counts Карта с обекти и техния брой срещания.
     * @param n Брой елементи за извеждане.
     * @param label Етикет за заглавието на статистиката.
     * @return Форматиран текст.
     */
    private String formatRanking(Map<String, Integer> counts, int n, String label) {
        if (counts.isEmpty())  return "Няма открити данни за посочения период";

        List<Map.Entry<String, Integer>> list = new ArrayList<>(counts.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        StringBuilder sb = new StringBuilder("Класация" + label + "\n");
        int limit = Math.min(n, list.size());

        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            sb.append((i + 1) + " " + entry.getKey() + ": " + entry.getValue() + " слушания\n");
        }
        return sb.toString();
    }

    /**
     * Намира плейлисти, чиято активност е под определен процент от общите слушания.
     *
     * @param playlists Списък с всички налични плейлисти.
     * @param history Списък с историята на слушанията.
     * @param threshold Процентен праг (напр. 10.0), под който активността се счита за ниска.
     * @return Списък с плейлистите под прага или съобщение за липса на данни.
     */
    public String getLowActivityPlaylists(List<Playlist> playlists,
                                          List<PlaylistHistory> history,
                                          double threshold) {
        if (history.isEmpty() || playlists.isEmpty())
            return "Няма налични данни за анализ на активността";

        StringBuilder result = new StringBuilder("Плейлисти с ниска активност (под " + threshold + "%):\n");
        int totalPlays = history.size();

        for (Playlist p : playlists) {
            int playsInPlaylist = 0;

            for (PlaylistHistory h : history) {
                if (p.getName().equals(h.getPlaylistName())) playsInPlaylist++;
            }

            double activityPercent = ((double) playsInPlaylist / totalPlays) * 100;

            if (activityPercent < threshold) {
                result.append(" ").append(p.getName())
                        .append(" (").append(String.format("%.1f", activityPercent)).append("%)\n");
            }
        }
        return result.toString();
    }

    /**
     * Изчислява най-активните плейлисти на база историята.
     *
     * @param history Списък с историята.
     * @param n Брой резултати.
     * @return Форматирана класация.
     */
    public String getTopPlaylists(List<PlaylistHistory> history, int n) {
        if (history.isEmpty()) return "Историята е празна";

        Map<String, Integer> counts = new HashMap<>();
        for (PlaylistHistory h : history) {
            String name = h.getPlaylistName();
            if (name != null) counts.put(name, counts.getOrDefault(name, 0) + 1);
        }
        return formatRanking(counts, n, "най-слушани плейлисти");
    }
}