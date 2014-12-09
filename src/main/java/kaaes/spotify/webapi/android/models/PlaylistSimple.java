package kaaes.spotify.webapi.android.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-object-simplified">Playlist object model (simplified)</a>
 */
public class PlaylistSimple {
    public boolean collaborative;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public List<Image> images;
    public String name;
    public UserSimple owner;
    @SerializedName("public")
    public Boolean is_public;
    public PlaylistTracksInformation tracks;
    public String type;
    public String uri;
}
