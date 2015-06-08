package kaaes.spotify.webapi.android.models;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-object-full">Playlist object model</a>
 */
public class Playlist extends PlaylistBase {
    public String description;
    public Followers followers;
    public Pager<PlaylistTrack> tracks;
}