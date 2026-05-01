package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за изтриване на песен съгласно спецификацията.
 * Формат: removesong <songId>
 */
public class RemoveSongCommand implements Command {
    private final PlaylistService service;
    public RemoveSongCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява изтриването по подадено ID.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 1) return "Употреба: removesong <songId>";
        try {
            int songId = Integer.parseInt(args[0]);
            return service.removeSong(songId);
        } catch (NumberFormatException e) {return "ID-то трябва да бъде число";}
    }
}