package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за разбъркване на плейлист съгласно спецификацията.
 * Формат: shuffle <playlistName> [seed=<n>]
 */
public class ShuffleCommand implements Command {
    private final PlaylistService service;

    /**
     * Конструктор на командата.
     * @param service Основният сървиз на приложението.
     */
    public ShuffleCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява разбъркването чрез разпознаване на името и опционалния seed.
     * @param args Аргументи от конзолата.
     * @return Съобщение за успех или грешка.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 1) return "Употреба: shuffle <playlistName> [seed=<n>]";

        String playlistName = args[0];
        Long seed = null;

        for (String arg : args) {
            if (arg.startsWith("seed=")) {
                try {
                    String seedValue = arg.split("=")[1];
                    seed = Long.parseLong(seedValue);
                } catch (Exception e) {return "Невалиден формат за seed, използвайте число";}
            }
        }
        return service.shuffle(playlistName, seed);
    }
}