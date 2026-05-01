package playlist;

import playlist.commands.*;
import playlist.services.PlaylistService;
import java.util.*;

/**
 * Основен клас за стартиране на програмата.
 */
public class Main {
    public static void main(String[] args) {
        PlaylistService service = new PlaylistService();
        Map<String, Command> commands = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        commands.put("open", new OpenCommand(service));
        commands.put("close", new CloseCommand(service));
        commands.put("save", new SaveCommand(service));
        commands.put("saveas", new SaveAsCommand(service));
        commands.put("addsong", new AddSongCommand(service));
        commands.put("play", new PlayCommand(service));
        commands.put("showhistory", new ShowHistoryCommand(service));
        commands.put("help", new HelpCommand());
        commands.put("addtoplaylist", new AddSongToPlaylistCommand(service));
        commands.put("toptracks", new TopTracksCommand(service));
        commands.put("dropplaylist", new DropPlaylistCommand(service));
        commands.put("lowactivity", new LowActivityCommand(service));
        commands.put("listsongs", new ListSongsCommand(service));
        commands.put("move", new MoveCommand(service));
        commands.put("shuffle", new ShuffleCommand(service));
        commands.put("removesong", new RemoveSongCommand(service));

        System.out.println("Music Manager работи. Напишете 'help' за помощ.");

        while (true) {
            System.out.print("> ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            String[] parts = input.split(" ");
            Command cmd = commands.get(parts[0].toLowerCase());

            if (cmd != null) System.out.println(cmd.execute(parts));
            else System.out.println("Неизвестна команда");
        }
        sc.close();
    }
}