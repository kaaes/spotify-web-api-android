package kaaes.spotify.webapi.android.models;

import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#user-object-private">User object model</a>
 */
public class User extends UserSimple {
    public String display_name;
    public String email;
    public Followers followers;
    public String country;
    public List<Image> images;
    public String product;
}
