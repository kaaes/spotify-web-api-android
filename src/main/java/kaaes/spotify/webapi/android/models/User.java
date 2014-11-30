package kaaes.spotify.webapi.android.models;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#user-object-private">User object model</a>
 */
public class User {
    public String display_name;
    public String email;
    public Map<String, String> external_urls;
    public Followers followers;
    public String href;
    public String id;
    public String country;
    public List<Image> images;
    public String product;
    public String type;
    public String uri;
}
