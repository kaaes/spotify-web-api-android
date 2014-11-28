package kaaes.spotify.webapi.android.models;

import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#paging-object">Paging object model</a>
 * @param <T>
 */
public class Pager<T> {
    public String href;
    public List<T> items;
    public int limit;
    public String next;
    public int offset;
    public String previous;
    public int total;
}
