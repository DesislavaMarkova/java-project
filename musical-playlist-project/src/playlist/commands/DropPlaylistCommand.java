package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за премахване на плейлист от музикалната система.
 */
public class DropPlaylistCommand implements Command {
    private PlaylistService service;
    public DropPlaylistCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява премахването на плейлист.
     * Очаква аргументи: dropplaylist <име на плейлист>.
     * @param args Аргументи от конзолата.
     * @return Текстово съобщение с резултата от операцията.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 2)  return "Използвайте: dropplaylist <име на плейлист>";

        String playlistName = args[1];

        return service.dropPlaylist(playlistName);
    }
}