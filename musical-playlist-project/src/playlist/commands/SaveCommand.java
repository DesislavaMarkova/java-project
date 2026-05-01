package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Записва промените обратно в текущия файл.
 */
public class SaveCommand implements Command {
    private PlaylistService service;
    public SaveCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява операцията по записване на промените в текущо отворения файл.
     * Проверява дали има зададен път към файл в сервиза и ако такъв липсва,
     * насочва потребителя към командата "save as".
     *
     * @param args Аргументи от конзолата (не са необходими за тази команда).
     * @return Текстово потвърждение за успешен запис или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        String path = service.getCurrentFileName();
        if (path == null) return "Няма зареден файл" + "Използвайте save as";
        try {
            service.saveAs(path);
            return "Successfully saved " + path;
        } catch (Exception e) {return "Грешка при save: " + e.getMessage();}
    }
}