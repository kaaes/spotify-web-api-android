package kaaes.spotify.webapi.android;

import java.util.Map;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Albums;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.NewReleases;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.FeaturedPlaylists;
import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.User;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface SpotifyService {

    /**
     * Profiles *
     */

    @GET("/me")
    public void getMe(Callback<User> callback);

    @GET("/me")
    public User getMe();

    /**
     * Playlists *
     */

    @GET("/users/{id}/playlists")
    public void getPlaylists(@Path("id") String userId, @Query("offset") int offset, @Query("limit") int limit, Callback<Pager<Playlist>> callback);

    @GET("/users/{id}/playlists")
    public Pager<Playlist> getPlaylists(@Path("id") String userId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/users/{id}/playlists")
    public void getPlaylists(@Path("id") String userId, Callback<Pager<Playlist>> callback);

    @GET("/users/{id}/playlists")
    public Pager<Playlist> getPlaylists(@Path("id") String userId);

    @GET("/users/{user_id}/playlists/{playlist_id}")
    public void getPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, Callback<Playlist> callback);

    @GET("/users/{user_id}/playlists/{playlist_id}")
    public Playlist getPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("offset") int offset, @Query("limit") int limit, Callback<Pager<Track>> callback);

    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public Pager<Track> getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, Callback<Pager<Track>> callback);

    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public Pager<Track> getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    /**
     * Albums *
     */

    @GET("/albums/{id}")
    public void getAlbum(@Path("id") String albumId, Callback<Album> callback);

    @GET("/albums/{id}")
    public Album getAlbum(@Path("id") String albumId);

    @GET("/albums")
    public void getAlbums(@Path("id") String albumIds, Callback<Albums> callback);

    @GET("/albums")
    public Albums getAlbums(@Query("ids") String albumIds);

    @GET("/albums/{id}/tracks")
    public Pager<Track> getAlbumTracks(@Path("id") String albumId);

    @GET("/albums/{id}/tracks")
    public void getAlbumTracks(@Path("id") String albumId, Callback<Pager<Track>> callback);

    @GET("/albums/{id}/tracks")
    public void getAlbumTracks(@Path("id") String albumId, @Query("offset") int offset, @Query("limit") int limit, Callback<Pager<Track>> callback);

    @GET("/albums/{id}/tracks")
    public Pager<Track> getAlbumTracks(@Path("id") String albumId, @Query("offset") int offset, @Query("limit") int limit);

    /**
     * Artists *
     */

    @GET("/artists/{id}/albums")
    public void getArtistAlbums(@Path("id") String artistId, Callback<Pager<Album>> callback, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/artists/{id}/albums")
    public Pager<Album> getArtistAlbums(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/artists/{id}/albums")
    public void getArtistAlbums(@Path("id") String artistId, Callback<Pager<Album>> callback);

    @GET("/artists/{id}/albums")
    public Pager<Album> getArtistAlbums(@Path("id") String artistId);

    @GET("/artists/{id}/top-tracks")
    public void getArtistTopTrack(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit, Callback<Pager<Track>> callback);

    @GET("/artists/{id}/top-tracks")
    public Pager<Track> getArtistTopTrack(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/artists/{id}/top-tracks")
    public void getArtistTopTrack(@Path("id") String artistId, Callback<Pager<Track>> callback);

    @GET("/artists/{id}/top-tracks")
    public Pager<Track> getArtistTopTrack(@Path("id") String artistId);

    @GET("/artists/{id}/related-artists")
    public void getRelatedArtists(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit, Callback<Pager<Artist>> callback);

    @GET("/artists/{id}/related-artists")
    public Pager<Artist> getRelatedArtists(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/artists/{id}/related-artists")
    public void getRelatedArtists(@Path("id") String artistId, Callback<Pager<Artist>> callback);

    @GET("/artists/{id}/related-artists")
    public Pager<Artist> getRelatedArtists(@Path("id") String artistId);

    @GET("/artists/{id}")
    public void getArtist(@Path("id") String artistId, Callback<Artist> callback);

    @GET("/artists/{id}")
    public Artist getArtist(@Path("id") String artistId);

    /**
     * Tracks *
     */

    @GET("/tracks/{id}")
    public void getTrack(@Path("id") String trackId, Callback<Track> callback);

    @GET("/tracks/{id}")
    public Track getTrack(@Path("id") String trackId);

    /**
     * Browse *
     */

    @GET("/browse/featured-playlists")
    public void getFeaturedPlaylists(Callback<FeaturedPlaylists> callback);

    @GET("/browse/featured-playlists")
    public FeaturedPlaylists getFeaturedPlaylists();

    @GET("/browse/featured-playlists")
    public void getFeaturedPlaylists(@QueryMap Map<String, String> options, Callback<FeaturedPlaylists> callback);

    @GET("/browse/featured-playlists")
    public FeaturedPlaylists getFeaturedPlaylists(@QueryMap Map<String, String> options);

    @GET("/browse/featured-playlists")
    public void getFeaturedPlaylists(@QueryMap Map<String, String> options, @Query("offset") int offset, @Query("limit") int limit, Callback<FeaturedPlaylists> callback);

    @GET("/browse/featured-playlists")
    public FeaturedPlaylists getFeaturedPlaylists(@QueryMap Map<String, String> options, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/browse/new-releases")
    public void getNewReleases(Callback<NewReleases> callback);

    @GET("/browse/new-releases")
    public NewReleases getNewReleases();

    @GET("/browse/new-releases")
    public void getNewReleases(@Query("country") String country, Callback<NewReleases> callback);

    @GET("/browse/new-releases")
    public NewReleases getNewReleases(@Query("country") String country);

    @GET("/browse/new-releases")
    public void getNewReleases(@Query("country") String country, @Query("offset") int offset, @Query("limit") int limit, Callback<NewReleases> callback);

    @GET("/browse/new-releases")
    public NewReleases getNewReleases(@Query("country") String country, @Query("offset") int offset, @Query("limit") int limit);


    /**
     * Library / Your Music *
     */

    @GET("/me/tracks")
    public void getMySavedTracks(Callback<Pager<SavedTrack>> callback);

    @GET("/me/tracks")
    public Pager<SavedTrack> getMySavedTracks();

    @GET("/me/tracks")
    public void getMySavedTracks(@Query("offset") int offset, @Query("limit") int limit, Callback<Pager<SavedTrack>> callback);

    @GET("/me/tracks")
    public Pager<SavedTrack> getMySavedTracks(@Query("offset") int offset, @Query("limit") int limit);

    @GET("/me/tracks/contains")
    public void containsMySavedTracks(@Query("ids") String ids, Callback<boolean[]> callback);

    @GET("/me/tracks/contains")
    public Boolean[] containsMySavedTracks(@Query("ids") String ids);

    // todo: read response status code
    @PUT("/me/tracks")
    public void addToMySavedTracks(@Query("ids") String ids, Callback<Boolean> callback);

    // todo: read response status code
    @PUT("/me/tracks")
    public boolean addToMySavedTracks(@Query("ids") String ids);

    // todo: read response status code
    @DELETE("/me/tracks")
    public void removeFromMySavedTracks(@Query("ids") String ids, Callback<Boolean> callback);

    // todo: read response status code
    @DELETE("/me/tracks")
    public boolean removeFromMySavedTracks(@Query("ids") String ids);

}
