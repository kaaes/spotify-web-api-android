package kaaes.spotify.webapi.android.models;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#user-object-public">User object (public) model</a>
 */
public class UserPublic {
    public String display_name;
    public Map<String, String> external_urls;
    public Followers followers;
    public String href;
    public String id;
    public List<Image> images;
    public String type;
    public String uri;
}
