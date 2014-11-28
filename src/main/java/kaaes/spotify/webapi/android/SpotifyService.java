package kaaes.spotify.webapi.android;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Albums;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.User;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface SpotifyService {

    /** Profiles **/

	@GET("/me")
	public void getMe(Callback<User> callback);

	@GET("/me")
	public User getMe();

    /** Playlists **/

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

    /** Albums **/

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

    /** Artists **/

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

	/** Tracks **/

    @GET("/tracks/{id}")
	public void getTrack(@Path("id") String trackId, Callback<Track> callback);

	@GET("/tracks/{id}")
	public Track getTrack(@Path("id") String trackId);
}
