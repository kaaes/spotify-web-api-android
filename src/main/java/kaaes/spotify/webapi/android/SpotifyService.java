package kaaes.spotify.webapi.android;

import java.util.Map;

import kaaes.spotify.webapi.android.annotations.DELETEWITHBODY;
import kaaes.spotify.webapi.android.models.*;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
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

    @GET("/user/{id}")
    public void getUser(@Path("id") String userId, Callback<User> callback);

    @GET("/user/{id}")
    public User getUser(@Path("id") String userId);

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
    public void getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("offset") int offset, @Query("limit") int limit, Callback<Pager<PlaylistTrack>> callback);

    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public Pager<PlaylistTrack> getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, Callback<Pager<PlaylistTrack>> callback);

    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    public Pager<PlaylistTrack> getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    @POST("/users/{user_id}/playlists")
    public Playlist createPlaylist(@Path("user_id") String userId, @Body Playlist playlist);

    @POST("/users/{user_id}/playlists")
    public void createPlaylist(@Path("user_id") String userId, @Body Playlist playlist, Callback<Playlist> callback);

    @POST("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris, Callback<SnapshotId> callback);

    @POST("/users/{user_id}/playlists/{playlist_id}/tracks")
    public SnapshotId addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris);

    @POST("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris, @Query("position") int position, Callback<SnapshotId> callback);

    @POST("/users/{user_id}/playlists/{playlist_id}/tracks")
    public SnapshotId addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris, @Query("position") int position);

    @DELETEWITHBODY("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemove tracksToRemove, Callback<SnapshotId> callback);

    @DELETEWITHBODY("/users/{user_id}/playlists/{playlist_id}/tracks")
    public SnapshotId removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemove tracksToRemove);

    @DELETEWITHBODY("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemoveWithPosition tracksToRemoveWithPosition, Callback<SnapshotId> callback);

    @DELETEWITHBODY("/users/{user_id}/playlists/{playlist_id}/tracks")
    public SnapshotId removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemoveWithPosition tracksToRemoveWithPosition);

    @PUT("/users/{user_id}/playlists/{playlist_id}/tracks")
    public void replaceTracksInPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris, Callback<Result> callback);

    @PUT("/users/{user_id}/playlists/{playlist_id}/tracks")
    public Result replaceTracksInPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris);

    @PUT("/users/{user_id}/playlists/{playlist_id}")
    public void changePlaylistDetails(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("name") String name, Callback<Result> callback);

    @PUT("/users/{user_id}/playlists/{playlist_id}")
    public Result changePlaylistDetails(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("name") String name);

    @PUT("/users/{user_id}/playlists/{playlist_id}")
    public void changePlaylistDetails(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("public") boolean is_public, Callback<Result> callback);

    @PUT("/users/{user_id}/playlists/{playlist_id}")
    public Result changePlaylistDetails(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("public") boolean is_public);

    @PUT("/users/{user_id}/playlists/{playlist_id}/followers")
    public void followPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, Callback<Result> callback);

    @PUT("/users/{user_id}/playlists/{playlist_id}/followers")
    public Result followPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    @PUT("/users/{user_id}/playlists/{playlist_id}/followers")
    public void followPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body PlaylistFollowPrivacy playlistFollowPrivacy, Callback<Result> callback);

    @PUT("/users/{user_id}/playlists/{playlist_id}/followers")
    public Result followPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body PlaylistFollowPrivacy playlistFollowPrivacy);

    @DELETE("/users/{user_id}/playlists/{playlist_id}/followers")
    public void unfollowPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, Callback<Result> callback);

    @DELETE("/users/{user_id}/playlists/{playlist_id}/followers")
    public Result unfollowPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    /**
     * Albums *
     */

    @GET("/albums/{id}")
    public void getAlbum(@Path("id") String albumId, Callback<Album> callback);

    @GET("/albums/{id}")
    public Album getAlbum(@Path("id") String albumId);

    @GET("/albums")
    public void getAlbums(@Query("ids") String albumIds, Callback<Albums> callback);

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
    public void getArtistAlbums(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit, Callback<Pager<Album>> callback);

    @GET("/artists/{id}/albums")
    public Pager<Album> getArtistAlbums(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/artists/{id}/albums")
    public void getArtistAlbums(@Path("id") String artistId, Callback<Pager<Album>> callback);

    @GET("/artists/{id}/albums")
    public Pager<Album> getArtistAlbums(@Path("id") String artistId);

    @GET("/artists/{id}/albums")
    public void getArtistAlbums(@Path("id") String artistId, @QueryMap Map<String, String> options, Callback<Pager<Album>> callback);

    @GET("/artists/{id}/albums")
    public Pager<Album> getArtistAlbums(@Path("id") String artistId, @QueryMap Map<String, String> options);

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

    @GET("/artists")
    public void getArtists(@Query("ids") String artistIds, Callback<Artists> callback);

    @GET("/artists")
    public Artists getArtists(@Query("ids") String artistIds);

    /**
     * Tracks *
     */

    @GET("/tracks/{id}")
    public void getTrack(@Path("id") String trackId, Callback<Track> callback);

    @GET("/tracks/{id}")
    public Track getTrack(@Path("id") String trackId);

    @GET("/tracks")
    public void getTracks(@Query("ids") String trackIds, Callback<Tracks> callback);

    @GET("/tracks")
    public Tracks getTracks(@Query("ids") String trackIds);

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

    @PUT("/me/tracks")
    public void addToMySavedTracks(@Query("ids") String ids, Callback<Object> callback);

    @PUT("/me/tracks")
    public Object addToMySavedTracks(@Query("ids") String ids);

    @DELETE("/me/tracks")
    public void removeFromMySavedTracks(@Query("ids") String ids, Callback<Object> callback);

    @DELETE("/me/tracks")
    public Object removeFromMySavedTracks(@Query("ids") String ids);

    /**
     * Follow *
     */

    @PUT("/me/following?type=user")
    public void followUsers(@Query("ids") String ids, Callback<Object> callback);

    @PUT("/me/following?type=user")
    public Object followUsers(@Query("ids") String ids);

    @PUT("/me/following?type=artist")
    public void followArtists(@Query("ids") String ids, Callback<Object> callback);

    @PUT("/me/following?type=artist")
    public Object followArtists(@Query("ids") String ids);

    @DELETE("/me/following?type=user")
    public void unfollowUsers(@Query("ids") String ids, Callback<Object> callback);

    @DELETE("/me/following?type=user")
    public Object unfollowUsers(@Query("ids") String ids);

    @DELETE("/me/following?type=artist")
    public void unfollowArtists(@Query("ids") String ids, Callback<Object> callback);

    @DELETE("/me/following?type=artist")
    public Object unfollowArtists(@Query("ids") String ids);

    @GET("/me/following/contains?type=user")
    public void isFollowingUsers(@Query("ids") String ids, Callback<boolean[]> callback);

    @GET("/me/following/contains?type=user")
    public Boolean[] isFollowingUsers(@Query("ids") String ids);

    @GET("/me/following/contains?type=artist")
    public void isFollowingArtists(@Query("ids") String ids, Callback<boolean[]> callback);

    @GET("/me/following/contains?type=artist")
    public Boolean[] isFollowingArtists(@Query("ids") String ids);

    /**
     * Search *
     */

    @GET("/search?type=track")
    public void searchTracks(@Query("q") String q, Callback<TracksPager> callback);

    @GET("/search?type=track")
    public TracksPager searchTracks(@Query("q") String q);

    @GET("/search?type=track")
    public void searchTracks(@Query("q") String q, @Query("market") String market, Callback<TracksPager> callback);

    @GET("/search?type=track")
    public TracksPager searchTracks(@Query("q") String q, @Query("market") String market);

    @GET("/search?type=track")
    public void searchTracks(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit, Callback<TracksPager> callback);

    @GET("/search?type=track")
    public TracksPager searchTracks(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/search?type=track")
    public void searchTracks(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit, Callback<TracksPager> callback);

    @GET("/search?type=track")
    public TracksPager searchTracks(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit);


    @GET("/search?type=artist")
    public void searchArtists(@Query("q") String q, Callback<ArtistsPager> callback);

    @GET("/search?type=artist")
    public ArtistsPager searchArtists(@Query("q") String q);

    @GET("/search?type=artist")
    public void searchArtists(@Query("q") String q, @Query("market") String market, Callback<ArtistsPager> callback);

    @GET("/search?type=artist")
    public ArtistsPager searchArtists(@Query("q") String q, @Query("market") String market);

    @GET("/search?type=artist")
    public void searchArtists(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit, Callback<ArtistsPager> callback);

    @GET("/search?type=artist")
    public ArtistsPager searchArtists(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/search?type=artist")
    public void searchArtists(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit, Callback<ArtistsPager> callback);

    @GET("/search?type=artist")
    public ArtistsPager searchArtists(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit);


    @GET("/search?type=album")
    public void searchAlbums(@Query("q") String q, Callback<AlbumsPager> callback);

    @GET("/search?type=album")
    public AlbumsPager searchAlbums(@Query("q") String q);

    @GET("/search?type=album")
    public void searchAlbums(@Query("q") String q, @Query("market") String market, Callback<AlbumsPager> callback);

    @GET("/search?type=album")
    public AlbumsPager searchAlbums(@Query("q") String q, @Query("market") String market);

    @GET("/search?type=album")
    public void searchAlbums(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit, Callback<AlbumsPager> callback);

    @GET("/search?type=album")
    public AlbumsPager searchAlbums(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/search?type=album")
    public void searchAlbums(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit, Callback<AlbumsPager> callback);

    @GET("/search?type=album")
    public AlbumsPager searchAlbums(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit);


    @GET("/search?type=playlist")
    public void searchPlaylists(@Query("q") String q, Callback<PlaylistsPager> callback);

    @GET("/search?type=playlist")
    public PlaylistsPager searchPlaylists(@Query("q") String q);

    @GET("/search?type=playlist")
    public void searchPlaylists(@Query("q") String q, @Query("market") String market, Callback<PlaylistsPager> callback);

    @GET("/search?type=playlist")
    public PlaylistsPager searchPlaylists(@Query("q") String q, @Query("market") String market);

    @GET("/search?type=playlist")
    public void searchPlaylists(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit, Callback<PlaylistsPager> callback);

    @GET("/search?type=playlist")
    public PlaylistsPager searchPlaylists(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/search?type=playlist")
    public void searchPlaylists(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit, Callback<PlaylistsPager> callback);

    @GET("/search?type=playlist")
    public PlaylistsPager searchPlaylists(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit);
}
