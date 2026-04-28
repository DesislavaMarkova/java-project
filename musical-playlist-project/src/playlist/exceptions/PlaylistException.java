/**
 * Персонализирано изключение за грешки, възникнали при управлението на плейлисти.
 */

package playlist.exceptions;

public class PlaylistException extends RuntimeException {
    public PlaylistException(String message) {
        super(message);
    }
}
