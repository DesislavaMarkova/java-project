package playlist.services;

import playlist.repository.FileRepository;
import playlist.models.*;
import java.util.List;

/**
 * Сервиз за управление на файлови операции.
 */
public class FileService {
    private final FileRepository repository = new FileRepository();

    /**
     * Зарежда данни от файл в предоставения обект PlaylistData.
     *
     * @param fileName Име на файла.
     * @param data Обект за съхранение на заредените данни.
     */
    public void load(String fileName, PlaylistData data) {
        try {
            Object[] loaded = repository.loadData(fileName);
            data.allSongs = (List<Song>) loaded[0];
            data.allPlaylists = (List<Playlist>) loaded[1];
            data.playbackHistory = (List<PlaylistHistory>) loaded[2];
            data.currentFileName = fileName;
        } catch (Exception e) {throw new RuntimeException("Грешка при зареждане");}
    }

    /**
     * Записва текущите данни във файл на посочения път.
     *
     * @param data Данните за запис.
     * @param path Път до файла.
     */
    public void save(PlaylistData data, String path) {
        try {
            repository.saveData(path, data.allSongs, data.allPlaylists, data.playbackHistory);
            data.currentFileName = path;
        } catch (Exception e) {throw new RuntimeException("Грешка при запис");}
    }
}