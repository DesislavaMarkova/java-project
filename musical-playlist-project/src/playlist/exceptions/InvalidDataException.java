package playlist.exceptions;

/**
 * Изключение за невалидни данни, подадени от потребителя.
 */
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {super(message);}
}