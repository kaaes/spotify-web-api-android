package kaaes.spotify.webapi.android.models;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#artist-object-full">Artist object model</a>
 */
public class Artist {
    public Map<String, String> external_urls;
    public Followers followers;
    public List<String> genres;
    public String href;
    public String id;
    public List<Image> images;
    public String name;
    public int popularity;
    public String type;
    public String uri;
}
