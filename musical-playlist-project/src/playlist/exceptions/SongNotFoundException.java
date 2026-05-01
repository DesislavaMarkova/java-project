package playlist.exceptions;

/**
 * Изключение, което се хвърля, когато търсената песен не е открита в системата.
 */
public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(String message) {super(message);}
}