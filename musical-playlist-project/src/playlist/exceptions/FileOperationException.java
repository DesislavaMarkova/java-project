package playlist.exceptions;

/**
 * Изключение за грешки, свързани с четене или запис на файлове.
 */
public class FileOperationException extends RuntimeException {
    public FileOperationException(String message) {super(message);}
}