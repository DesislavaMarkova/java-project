package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за добавяне на съществуваща песен от общия списък в конкретен плейлист.
 */
public class AddSongToPlaylistCommand implements Command {
    private PlaylistService service;

    public AddSongToPlaylistCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява логиката по добавяне на песен към плейлист.
     * Проверява броя на аргументите и се опитва да преобразува ID-то на песента в число.
     *
     * @param args Аргументи, подадени от конзолата: [0] име на команда, [1] име на плейлист, [2] ID на песен.
     * @return Текстово съобщение за успешно добавяне или описание на възникнала грешка.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 3)
            return "Използвайте: addtoplaylist <име на плейлист> <id на песен>";

        try {
            String playlistName = args[1];
            int songId = Integer.parseInt(args[2]);

            service.addSongToPlaylist(playlistName, songId, null);
            return "Песента с ID " + songId + " беше добавена в плейлист'" + playlistName;
        }
        catch (NumberFormatException e) {return "ID-то на песента трябва да бъде число";}
        catch (Exception e) {return "Грешка: " + e.getMessage();}
    }
}