package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за извеждане на детайлна информация за конкретна песен.
 * Тази команда позволява на потребителя да види всички метаданни за песен,
 * включително тези, които са опционални (албум, година, жанр).
 */
public class SongInfoCommand implements Command {
    private final PlaylistService service;
    public SongInfoCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява логиката на командата чрез обработка на подаденото ID на песен.
     * Очаква се точно един аргумент – уникалният идентификатор на песента.
     *
     * @param args Аргументи от командния ред. Очаква се args[0] да бъде числово ID.
     * @return Резултатът от търсенето – пълна информация за песента или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 1) return "Употреба: songinfo <songId>";

        try {
            int id = Integer.parseInt(args[0]);
            return service.songInfo(id);
        } catch (NumberFormatException e) {return "ID-то трябва да бъде число";}
    }
}