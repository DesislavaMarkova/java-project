package playlist.commands;

/**
 * Команда за извеждане на помощна информация за всички налични команди.
 */
public class HelpCommand implements Command {
    @Override
    public String execute(String[] args) {
        return "Поддържани команди:\n" +
                "  open <file> - Отваря файл с данни\n" +
                "  save - Записва промените\n" +
                "  saveas <file> - Записва в нов файл\n" +
                "  close - Затваря текущия файл\n" +
                "  addsong <title> <artist> <duration> [album] [year] [genre] - Добавя песен\n" +
                "  removesong <songId> - Изтрива песен от системата\n" +
                "  listsongs [filter=val] - Списък с песни (филтри: artist, genre, year)\n" +
                "  songinfo <songId> - Детайли за песен\n" +
                "  createplaylist <name> - Нов плейлист\n" +
                "  showplaylist <name> - Съдържание на плейлист\n" +
                "  dropplaylist <name> - Изтрива плейлист\n" +
                "  addtoplaylist <pName> <sId> [pos] - Добавя песен в плейлист\n" +
                "  move <pName> <fPos> <tPos> - Премества песен в плейлист\n" +
                "  shuffle <pName> [seed=n] - Разбърква плейлист\n" +
                "  play <songId> <pName> - Слушане на песен\n" +
                "  showhistory - История на слушанията\n" +
                "  lowactivity <f> <t> <% > - Анализ на слаба активност\n" +
                "  toptracks <n> - Топ най-слушани песни\n" +
                "  topartists <n> - Топ най-слушани изпълнители\n" +
                "  topplaylists <n> - Класация на активни плейлисти\n" +
                "  exit - Изход от програмата";
    }
}