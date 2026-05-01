package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за извеждане на статистическа информация за най-слушаните изпълнители.
 * Поддържа филтриране по брой резултати и опционален времеви период.
 * Формат: topartists <n> [from=<date>] [to=<date>]
 */
public class TopArtistsCommand implements Command {
    private final PlaylistService service;
    public TopArtistsCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява командата за извличане на топ изпълнители.
     * Анализира масива от аргументи, за да извлече броя (n) и филтрите за дата.
     *
     * @param args Аргументи на командата. Първият аргумент трябва да е число.
     *             Следващите могат да бъдат във формат from=yyyy-MM-dd или to=yyyy-MM-dd.
     * @return Форматиран списък с най-слушаните изпълнители или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 1) return "Употреба: topartists <n> [from=<date>] [to=<date>]";

        try {
            int n = Integer.parseInt(args[0]);
            String from = null;
            String to = null;

            for (String arg : args) {
                if (arg.startsWith("from=")) from = arg.split("=")[1];
                if (arg.startsWith("to=")) to = arg.split("=")[1];
            }

            return service.topArtists(n, from, to);
        } catch (NumberFormatException e) {return "Параметърът <n> трябва да бъде число";}
    }
}