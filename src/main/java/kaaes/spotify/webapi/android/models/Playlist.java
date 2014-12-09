package kaaes.spotify.webapi.android.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-object-full">Playlist object model</a>
 */
public class Playlist extends PlaylistSimple{
    public String description;
    public Followers followers;
    public String snapshot_id;
    public Pager<PlaylistTrack> tracks;
}