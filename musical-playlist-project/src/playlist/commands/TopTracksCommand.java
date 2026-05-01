package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за извеждане на най-слушаните песни.
 */
public class TopTracksCommand implements Command {
    private PlaylistService service;
    public TopTracksCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява логиката за извеждане на класация на най-слушаните песни.
     * Проверява дали потребителят е подал число като аргумент за броя на песните (N),
     * като по подразбиране показва топ 5, ако не е посочено друго.
     *
     * @param args Аргументи от конзолата, където args[1] (опционално) е броят на песните.
     * @return Текстово представяне на класацията или съобщение за грешка при невалидно число.
     */
    @Override
    public String execute(String[] args) {
        int n = 5;
        if (args.length > 1) {
            try {n = Integer.parseInt(args[1]);}
            catch (NumberFormatException e) {return "Броят трябва да е число";}
        }
        return service.topTracks(n);
    }
}