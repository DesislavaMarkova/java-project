package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за извеждане на най-популярните плейлисти.
 */
public class TopPlaylistsCommand implements Command {
    private final PlaylistService service;

    public TopPlaylistsCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява командата за извличане на топ плейлисти.
     * @param args Аргументи от конзолата (очаква се n).
     * @return Резултат от статистиката.
     */
    @Override
    public String execute(String[] args) {
        int n = 5;
        if (args.length > 1) {
            try {n = Integer.parseInt(args[1]);}
            catch (NumberFormatException e) {return "Въведете валидно число за брой плейлисти";}
        }
        return service.topPlaylists(n);
    }
}