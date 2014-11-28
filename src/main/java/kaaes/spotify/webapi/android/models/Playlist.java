package kaaes.spotify.webapi.android.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-object-full">Playlist object model</a>
 */
public class Playlist {
    public boolean collaborative;
    public String description;
    public Map<String,String> external_urls;
    public Followers followers;
    public String href;
    public String id;
    public List<Image> images;
    public String name;
    public User owner;
    @SerializedName("public")
    public boolean is_public;
    public String snapshot_id;
    public Pager<Track> tracks;
    public String type ;
    public String uri;
}
