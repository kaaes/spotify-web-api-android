package kaaes.spotify.webapi.android.models;

import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#user-object-public">User object model</a>
 */
public class UserPublic extends UserSimple {
    public String display_name;
    public Followers followers;
    public List<Image> images;
}
