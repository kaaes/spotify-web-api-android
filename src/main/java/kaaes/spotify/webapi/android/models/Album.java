package kaaes.spotify.webapi.android.models;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#album-object-full">Album object model</a>
 */
public class Album {
    public String album_type;
    public List<Artist> artists;
    public List<String> available_markets;
    public List<Copyright> copyrights;
    public Map<String,String> external_ids;
    public Map<String,String> external_urls;
    public List<String> genres;
    public String href;
    public String id;
    public List<Image> images;
	public String name;
    public int popularity;
	public String release_date;
	public String release_date_precision;
	public Pager<Track> tracks;
    public String type;
    public String uri;
}
