package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за показване на съдържанието на конкретен плейлист.
 */
public class ShowPlaylistCommand implements Command {
    private final PlaylistService service;
    public ShowPlaylistCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява командата за извеждане на списък с песни от плейлист.
     * @param args Аргументи от конзолата (очаква се име на плейлист).
     * @return Форматирано съдържание на плейлиста или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            return "Употреба: showplaylist <име>";
        }
        return service.showPlaylist(args[1]);
    }
}
