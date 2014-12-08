package kaaes.spotify.webapi.android.models;

public class NewReleases {
    public Pager<Album> albums;
    public int limit;
    public String next;
    public int offset;
    public String previous;
    public int total;
}
