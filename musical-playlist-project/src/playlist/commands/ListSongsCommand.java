package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за извеждане на списък с песни с опции за филтриране.
 */
public class ListSongsCommand implements Command {
    private final PlaylistService service;

    public ListSongsCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява командата, като анализира аргументите за филтриране.
     * Пример: listsongs artist=Abba genre=Pop
     */
    @Override
    public String execute(String[] args) {
        String artist = null;
        String genre = null;
        Integer year = null;

        for (String arg : args) {
            if (arg.contains("=")) {
                String[] parts = arg.split("=");
                if (parts.length < 2) continue;

                String key = parts[0].toLowerCase();
                String value = parts[1];

                if (key.equals("artist")) artist = value;
                else if (key.equals("genre")) genre = value;
                else if (key.equals("year")) {
                    try {
                        year = Integer.parseInt(value);
                    } catch (NumberFormatException e) { return "Годината трябва да е число";}
                }
            }
        }
        return service.listSongs(artist, genre, year);
    }
}