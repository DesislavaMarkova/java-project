package playlist.repository;

import java.io.*;
import java.util.List;
import playlist.models.Song;
import playlist.models.Playlist;
import playlist.models.PlaylistHistory;

/**
 * Клас за работа с файловата система.
 * Записва и чете данните на приложението в бинарен формат.
 */
public class FileRepository {
    /**
     * Записва списъците с данни в посочения файл.
     * @param path Път до файла на диска.
     * @param songs Списък с всички песни.
     * @param playlists Списък с всички плейлисти.
     * @param history История на слушанията.
     * @throws IOException При грешка по време на записа.
     */
    public void saveData(String path, List<Song> songs, List<Playlist> playlists,
                         List<PlaylistHistory> history) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(songs);
        oos.writeObject(playlists);
        oos.writeObject(history);
        oos.close();
    }

    /**
     * Зарежда обектите от бинарен файл.
     * @param path Път до файла.
     * @return Масив от обекти, които след това ще се кастнат в сервиза.
     * @throws Exception При грешка при четенето или несъществуващ файл.
     */
    public Object[] loadData(String path) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        Object songs = ois.readObject();
        Object playlists = ois.readObject();
        Object history = ois.readObject();
        ois.close();

        return new Object[]{songs, playlists, history};
    }
}
