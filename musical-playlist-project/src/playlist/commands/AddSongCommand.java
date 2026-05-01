package playlist.commands;

import playlist.services.PlaylistService;
import playlist.models.Song;
import playlist.models.Genre;

/**
 * Команда за добавяне на нова песен в музикалната система.
 * Извлича данни от потребителския вход и валидира съществуването на песента.
 */
public class AddSongCommand implements Command {
    private PlaylistService service;

    /**
     * Конструктор за инициализиране на командата със споделения сервиз.
     * @param service Инстанция на PlaylistService за достъп до данните.
     */
    public AddSongCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява операцията по добавяне на песен.
     * Очаква аргументи: title, artist, duration, genre.
     * @param args Масив от низове, подадени от конзолата.
     * @return Текстово съобщение за резултата от операцията.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 5)
            return "Невалидни аргументи!" +
                    "Използвайте: addsong <заглавие> <изпълнител> <време> <жанр>";

        String title = args[1];
        String artist = args[2];
        String duration = args[3];

        try {
            Genre genre = Genre.valueOf(args[4].toUpperCase());

            for (Song s : service.getAllSongs()) {
                if (s.getTitle().equalsIgnoreCase(title) && s.getArtist().equalsIgnoreCase(artist))
                    return "Песента вече съществува!";

            }
            int newId = service.getAllSongs().size() + 1;
            Song song = new Song(newId, title, artist, "Unknown", duration, 2024, genre);
            service.getAllSongs().add(song);

            return "Песента '" + title + "' беше добавена успешно с ID: " + newId;
        } catch (IllegalArgumentException e) {return "Невалиден жанр";}
    }
}