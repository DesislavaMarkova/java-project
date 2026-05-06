package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за изтриване на песен съгласно спецификацията.
 * Формат: removesong <songId>
 */
public class RemoveSongCommand implements Command {
    private final PlaylistService service;
    public RemoveSongCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява командата за премахване на песен от системата.
     * Валидира подадения аргумент и го преобразува в числово ID.
     *
     * @param args Аргументи от конзолата, където args[1] е идентификаторът на песента.
     * @return Текст с резултата от изтриването или съобщение за грешка при невалиден вход.
     */
    @Override
    public String execute(String[] args) {
        if (args.length < 2) return "Употреба: removesong <id>";
        try {
            int id = Integer.parseInt(args[1].trim());
            return service.removeSong(id);
        } catch (NumberFormatException e) {
            return "Грешка: ID-то трябва да бъде число";
        }
    }
}