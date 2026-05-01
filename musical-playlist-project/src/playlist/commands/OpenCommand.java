package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за отваряне на файл. Зарежда данните в паметта.
 */
public class OpenCommand implements Command {
    private PlaylistService service;
    public OpenCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява операцията по отваряне на файл.
     * Проверява дали е подадено име на файл и извиква зареждащата логика в сервиза.
     * @param args Аргументи от конзолата, където args[1] е пътят до файла.
     * @return Текстово съобщение за успешно отваряне или описание на грешка при неуспех.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 2) return "Не е посочен файл!";
        try {
            service.open(args[1]);
            return "Successfully opened " + args[1];
        } catch (Exception e) {return "Файлът не може да бъде отворен";}
    }
}