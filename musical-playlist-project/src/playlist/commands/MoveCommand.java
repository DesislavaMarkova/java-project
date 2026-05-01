package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за преместване на песен в рамките на плейлист.
 */
public class MoveCommand implements Command {
    private final PlaylistService service;
    public MoveCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява преместването въз основа на подадени позиции.
     * Параметри: <playlistName> <fromPos> <toPos>
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 3) return "Употреба: move <playlistName> <fromPos> <toPos>";

        try {
            String playlistName = args[0];
            int fromPos = Integer.parseInt(args[1]);
            int toPos = Integer.parseInt(args[2]);

            return service.moveSong(playlistName, fromPos, toPos);
        } catch (NumberFormatException e) {return "Позициите трябва да бъдат цели числа";}
    }
}