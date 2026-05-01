package playlist.commands;

import playlist.services.PlaylistService;

/**
 * Команда за затваряне на текущия файл и изчистване на заредената информация в паметта.
 */
public class CloseCommand implements Command {
    private PlaylistService service;
    public CloseCommand(PlaylistService service) {this.service = service;}

    /**
     * Изпълнява операцията по затваряне.
     * Взима името на текущия файл за потвърждение и изчиства данните чрез сервиза.
     * @param args Аргументи от конзолата (не са необходими за тази команда).
     * @return Текстово потвърждение за успешно затворен файл.
     */
    @Override
    public String execute(String[] args) {
        String fileName = service.getCurrentFileName();
        service.close();
        return "Successfully closed " + (fileName != null ? fileName : "file");
    }
}