package playlist.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PlaylistHistory implements Serializable {
    private Song song;
    private LocalDateTime playbackTime;
    private String playlistName;

    public PlaylistHistory(Song song, String playlistName) {
        this.song = song;
        this.playbackTime = LocalDateTime.now();
        this.playlistName = playlistName;
    }

    public Song getSong() {return song;}
    public LocalDateTime getPlaybackTime() {return playbackTime;}
    public String getPlaylistName() {return playlistName;}
}
