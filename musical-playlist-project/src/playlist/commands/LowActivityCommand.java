package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за анализ на ниска активност съгласно изискванията на проекта.
 */
public class LowActivityCommand implements Command {
    private final PlaylistService service;

    /**
     * Конструктор на командата.
     * @param service Подаване на основния сервиз.
     */
    public LowActivityCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява командата с подадени параметри за период и праг.
     * @param args Аргументи: [0] от дата, [1] до дата, [2] праг процент.
     * @return Резултат от анализа.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 3)
            return "Употреба: lowactivity <from> <to> <thresholdPercent>";

        try {
            String from = args[0];
            String to = args[1];
            double threshold = Double.parseDouble(args[2]);

            return service.lowActivity(from, to, threshold);
        } catch (NumberFormatException e) {return "Прагът трябва да бъде число";}
    }
}