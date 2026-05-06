package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за преместване на песен в рамките на конкретен плейлист.
 */
public class MoveCommand implements Command {
    private final PlaylistService service;
    public MoveCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява преместването на песен от една позиция на друга.
     * Очаква аргументи: [1]име на плейлист, [2]начална позиция, [3]крайна позиция.
     *
     * @param args Масив от думи, въведени в конзолата.
     * @return Текст с потвърждение или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 4) return "Употреба: move <playlistName> <fromPos> <toPos>";

        try {
            String playlistName = args[1];
            int fromPos = Integer.parseInt(args[2].trim());
            int toPos = Integer.parseInt(args[3].trim());

            return service.moveSong(playlistName, fromPos, toPos);
        } catch (NumberFormatException e) {return "Позициите трябва да бъдат цели числа.";}
    }
}