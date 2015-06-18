package kaaes.spotify.webapi.android.models;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-track-object">Playlist track object model</a>
 */
public class PlaylistTrack {
    public String added_at;
    public UserPublic added_by;
    public Track track;
    public Boolean is_local;
}
