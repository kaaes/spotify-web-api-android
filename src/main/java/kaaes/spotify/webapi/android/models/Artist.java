package kaaes.spotify.webapi.android.models;

import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#artist-object-full">Artist object model</a>
 */
public class Artist extends ArtistSimple {
    public Followers followers;
    public List<String> genres;
    public List<Image> images;
    public Integer popularity;
}