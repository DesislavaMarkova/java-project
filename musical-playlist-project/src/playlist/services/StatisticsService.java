package playlist.services;

import playlist.models.*;
import java.util.*;

/**
 * Сервиз за генериране на статистики и анализи за слушанията.
 * Изчислява популярност на песни и активност на плейлистите.
 */
public class StatisticsService {

    /**
     * Изчислява най-слушаните песни въз основа на историята.
     *
     * @param history Списък с цялата история на слушанията.
     * @param n Максимален брой песни за извеждане.
     * @return Форматиран текст със заглавията на песните и броя им слушания.
     */
    public String getTopTracks(List<PlaylistHistory> history, int n) {
        if (history.isEmpty()) return "Няма данни за слушания";

        Map<String, Integer> counts = new HashMap<>();
        for (PlaylistHistory record : history) {
            String title = record.getSong().getTitle();
            counts.put(title, counts.getOrDefault(title, 0) + 1);
        }

        List<Map.Entry<String, Integer>> sortedTracks = new ArrayList<>(counts.entrySet());
        sortedTracks.sort((a, b)
                -> b.getValue().compareTo(a.getValue()));

        StringBuilder sb = new StringBuilder("Топ най-слушани песни:\n");
        int limit = Math.min(n, sortedTracks.size());

        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = sortedTracks.get(i);
            sb.append(" ").append(entry.getKey()).append(": ")
                    .append(entry.getValue()).append(" слушания\n");
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
}