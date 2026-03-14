package exceptions;

public class PlaylistAlreadyExistsException extends RuntimeException {
    public PlaylistAlreadyExistsException(String message) {
        super(message);
    }
}
