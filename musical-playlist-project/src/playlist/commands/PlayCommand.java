package playlist.commands;

import playlist.services.PlaylistService;
import playlist.models.Song;
import playlist.models.PlaylistHistory;

/**
 * Команда за симулиране на възпроизвеждане на песен.
 * Записва събитието в историята на слушанията на потребителя.
 */
public class PlayCommand implements Command {
    private PlaylistService service;
    public PlayCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява командата за пускане на песен по зададено ID.
     * @param args Трябва да съдържа ID на песента като втори елемент.
     * @return Информация за песента, която е пусната, или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 2) return "Въведете ID на песен";
        try {
            int id = Integer.parseInt(args[1]);
            Song foundSong = null;
            for (Song s : service.getAllSongs()) {
                if (s.getId() == id) {
                    foundSong = s;
                    break;
                }
            }
            if (foundSong == null) {return "Не е намерена песен с ID" + id;}

            service.getHistory().add(new PlaylistHistory(foundSong, "Manual Play"));
            return "В момента слушате: " + foundSong.getArtist() + " " + foundSong.getTitle();

        } catch (NumberFormatException e) {return "ID трябва да бъде число";}
    }
}