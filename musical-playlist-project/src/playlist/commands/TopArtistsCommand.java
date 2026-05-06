package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за извеждане на класация на най-слушаните изпълнители.
 */
public class TopArtistsCommand implements Command {
    private final PlaylistService service;
    public TopArtistsCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява статистически анализ за най-популярните изпълнители.
     *
     * @param args Аргументи: [1] брой резултати (n).
     * @return Форматиран списък с изпълнители или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 2) return "Употреба: topartists <n>";

        try {
            int n = Integer.parseInt(args[1].trim());
            return service.topArtists(n, null, null);
        } catch (NumberFormatException e) {
            return "Грешка: Параметърът <n> трябва да бъде число";
        }
    }
}