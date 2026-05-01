package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Записва данните в нов файл, посочен от потребителя.
 */
public class SaveAsCommand implements Command {
    private PlaylistService service;
    public SaveAsCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява операцията по записване на данните в нов файл (Save As).
     * Проверява дали е подаден път до файл в аргументите и извиква
     * съответната логика в сервиза за съхранение на информацията.
     *
     * @param args Аргументи от конзолата, където args[1] е пътят за новия файл.
     * @return Текстово потвърждение за успешен запис или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 2) return "Посочете име на файл";
        try {
            service.saveAs(args[1]);
            return "Successfully saved as " + args[1];
        } catch (Exception e) {
            return "Грешка при save as: " + e.getMessage();
        }
    }
}