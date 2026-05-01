package playlist.commands;

/**
 * Команда за прекратяване работата на приложението.
 */
public class ExitCommand implements Command {
    @Override
    public String execute(String[] args) {
        return "Exiting the program...";
    }
}
