package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за създаване на нов празен плейлист в музикалната система.
 * Извлича името и описанието от аргументите, подадени от потребителя.
 */
public class CreatePlaylistCommand implements Command {
    private PlaylistService service;
    public CreatePlaylistCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява операцията по създаване на плейлист.
     * Очаква аргументи: <име> <описание>.
     * @param args Аргументи от конзолата.
     * @return Резултат от операцията като текст.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 3) return "Невалидни аргументи!"
                             + "Използвайте: createplaylist <име> <описание>";

        String name = args[1];
        String description = args[2];

        return service.createPlaylist(name, description);
    }
}