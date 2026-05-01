package playlist.commands;

import playlist.services.PlaylistService;
import playlist.models.PlaylistHistory;

/**
 * Команда за извеждане на хронологичен списък на всички слушани песни.
 */
public class ShowHistoryCommand implements Command {
    private PlaylistService service;
    public ShowHistoryCommand(PlaylistService service) {this.service = service;}
    /**
     * Форматира и връща пълната история на слушанията в чист текстов вид.
     * Обхожда списъка с хронология и извежда времето, изпълнителя и заглавието на всяка песен.
     *
     * @param args Не изисква допълнителни параметри от конзолата.
     * @return Списък с историята или съобщение, че тя е празна.
     */
    @Override
    public String execute(String[] args) {
        if (service.getHistory().isEmpty()) return "историята е празна";

        StringBuilder sb = new StringBuilder("Слушани песни:\n");

        for (PlaylistHistory h : service.getHistory()) {
            String time = h.getPlaybackTime().toString();
            String artist = h.getSong().getArtist();
            String title = h.getSong().getTitle();

            sb.append("[" + time + "] " + artist + " - " + title + "\n");
        }
        return sb.toString();
    }
}