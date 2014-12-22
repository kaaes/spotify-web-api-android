package kaaes.spotify.webapi.android;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Albums;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.FeaturedPlaylists;
import kaaes.spotify.webapi.android.models.NewReleases;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.PlaylistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.models.User;
import kaaes.spotify.webapi.android.models.UserSimple;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SpotifyServiceTest {

    private SpotifyService mSpotifyService;
    private Client mMockClient;
    private Gson mGson;

    private class MatchesId extends ArgumentMatcher<Request> {

        private final String mId;

        MatchesId(String id) {
            mId = id;
        }

        public boolean matches(Object request) {
            try {
                return ((Request) request).getUrl().contains(URLEncoder.encode(mId, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return false;
            }
        }
    }

    @Before
    public void setUp() {
        mMockClient = mock(Client.class);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(mMockClient)
                .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
                .build();

        mSpotifyService = restAdapter.create(SpotifyService.class);
        mGson = new GsonBuilder().create();
    }

    @Test
    public void shouldGetTrackData() throws IOException {
        String body = TestUtils.readTestData("track.json");
        Track fixture = mGson.fromJson(body, Track.class);

        Response response = TestUtils.getResponseFromModel(fixture, Track.class);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        Track track = mSpotifyService.getTrack(fixture.id);
        this.compareJSONWithoutNulls(body, track);
    }

    @Test
    public void shouldGetMultipleTrackData() throws IOException {
        String body = TestUtils.readTestData("tracks.json");
        Tracks fixture = mGson.fromJson(body, Tracks.class);

        String ids = "";
        for (int i = 0; i < fixture.tracks.size(); i++) {
            if (i > 0) {
                ids += ",";
            }
            ids += fixture.tracks.get(i).id;
        }

        Response response = TestUtils.getResponseFromModel(fixture, Tracks.class);
        when(mMockClient.execute(argThat(new MatchesId(ids)))).thenReturn(response);

        Tracks tracks = mSpotifyService.getTracks(ids);
        this.compareJSONWithoutNulls(body, tracks);
    }

    @Test
    public void shouldGetAlbumData() throws IOException {
        String body = TestUtils.readTestData("album.json");
        Album fixture = mGson.fromJson(body, Album.class);

        Response response = TestUtils.getResponseFromModel(fixture, Album.class);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        Album album = mSpotifyService.getAlbum(fixture.id);
        this.compareJSONWithoutNulls(body, album);
    }

    @Test
    public void shouldGetMultipleAlbumData() throws IOException {
        String body = TestUtils.readTestData("albums.json");
        Albums fixture = mGson.fromJson(body, Albums.class);

        String ids = "";
        for (int i = 0; i < fixture.albums.size(); i++) {
            if (i > 0) {
                ids += ",";
            }
            ids += fixture.albums.get(i).id;
        }

        Response response = TestUtils.getResponseFromModel(fixture, Albums.class);
        when(mMockClient.execute(argThat(new MatchesId(ids)))).thenReturn(response);

        Albums albums = mSpotifyService.getAlbums(ids);
        this.compareJSONWithoutNulls(body, albums);
    }

    @Test
    public void shouldGetArtistData() throws IOException {
        String body = TestUtils.readTestData("artist.json");
        Artist fixture = mGson.fromJson(body, Artist.class);

        Response response = TestUtils.getResponseFromModel(fixture, Artist.class);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        Artist artist = mSpotifyService.getArtist(fixture.id);
        this.compareJSONWithoutNulls(body, artist);
    }

    @Test
    public void shouldGetMultipleArtistData() throws IOException {
        String body = TestUtils.readTestData("artists.json");
        Artists fixture = mGson.fromJson(body, Artists.class);

        String ids = "";
        for (int i = 0; i < fixture.artists.size(); i++) {
            if (i > 0) {
                ids += ",";
            }
            ids += fixture.artists.get(i).id;
        }

        Response response = TestUtils.getResponseFromModel(fixture, Artists.class);
        when(mMockClient.execute(argThat(new MatchesId(ids)))).thenReturn(response);

        Artists artists = mSpotifyService.getArtists(ids);

        this.compareJSONWithoutNulls(body, artists);
    }

    @Test
    public void shouldGetArtistsAlbumsData() throws IOException {
        Type modelType = new TypeToken<Pager<Album>>(){}.getType();

        String artistId = "1vCWHaC5f2uS3yhpwWbIA6";
        String body = TestUtils.readTestData("artist-album.json");
        Pager<Album> fixture = mGson.fromJson(body, modelType);

        Response response = TestUtils.getResponseFromModel(fixture, modelType);
        when(mMockClient.execute(argThat(new MatchesId(artistId)))).thenReturn(response);

        Pager<Album> albums = mSpotifyService.getArtistAlbums(artistId);

        this.compareJSONWithoutNulls(body, albums);
    }

    @Test
    public void shouldGetPlaylistData() throws IOException {
        String body = TestUtils.readTestData("playlist-response.json");
        Playlist fixture = mGson.fromJson(body, Playlist.class);

        Response response = TestUtils.getResponseFromModel(fixture, Playlist.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        Playlist playlist = mSpotifyService.getPlaylist(fixture.owner.id, fixture.id);
        compareJSONWithoutNulls(body, playlist);
    }

    @Test
    public void shouldGetPlaylistTracks() throws IOException {
        Type modelType = new TypeToken<Pager<PlaylistTrack>>(){}.getType();

        String body = TestUtils.readTestData("playlist-tracks.json");
        Pager<PlaylistTrack> fixture = mGson.fromJson(body, modelType);

        Response response = TestUtils.getResponseFromModel(fixture, modelType);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        Pager<PlaylistTrack> playlistTracks = mSpotifyService.getPlaylistTracks("test", "test");
        compareJSONWithoutNulls(body, playlistTracks);
    }

    @Test
    public void shouldGetNewReleases() throws IOException {
        final String countryId = "SE";
        final int limit = 5;

        String body = TestUtils.readTestData("new-releases.json");
        NewReleases fixture = mGson.fromJson(body, NewReleases.class);

        Response response = TestUtils.getResponseFromModel(fixture, NewReleases.class);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {

                try {
                    return ((Request) argument).getUrl().contains("limit=" + limit) &&
                            ((Request) argument).getUrl().contains("country=" + URLEncoder.encode(countryId, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            }
        }))).thenReturn(response);

        NewReleases newReleases = mSpotifyService.getNewReleases(countryId, 0, limit);

        this.compareJSONWithoutNulls(body, newReleases);
    }

    @Test
    public void shouldGetFeaturedPlaylists() throws IOException {
        final String countryId = "SE";
        final String locale = "sv_SE";
        final int limit = 5;

        String body = TestUtils.readTestData("featured-playlists.json");
        FeaturedPlaylists fixture = mGson.fromJson(body, FeaturedPlaylists.class);

        Response response = TestUtils.getResponseFromModel(fixture, FeaturedPlaylists.class);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {

                try {
                    return ((Request) argument).getUrl().contains("limit=" + limit) &&
                            ((Request) argument).getUrl().contains("country=" + URLEncoder.encode(countryId, "UTF-8")) &&
                            ((Request) argument).getUrl().contains("locale=" + URLEncoder.encode(locale, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            }
        }))).thenReturn(response);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("country", countryId);
        map.put("locale", locale);
        FeaturedPlaylists featuredPlaylists = mSpotifyService.getFeaturedPlaylists(map, 0, limit);

        this.compareJSONWithoutNulls(body, featuredPlaylists);
    }

    @Test
    public void shouldGetUserData() throws IOException {
        String body = TestUtils.readTestData("user.json");
        UserSimple fixture = mGson.fromJson(body, UserSimple.class);

        Response response = TestUtils.getResponseFromModel(fixture, UserSimple.class);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        UserSimple userSimple = mSpotifyService.getUser(fixture.id);
        this.compareJSONWithoutNulls(body, userSimple);
    }

    @Test
    public void shouldGetCurrentUserData() throws IOException {
        String body = TestUtils.readTestData("current-user.json");
        User fixture = mGson.fromJson(body, User.class);

        Response response = TestUtils.getResponseFromModel(fixture, User.class);
        when(mMockClient.execute(Matchers.<Request>any())).thenReturn(response);

        User user = mSpotifyService.getMe();
        this.compareJSONWithoutNulls(body, user);
    }

    @Test
    public void shouldCheckFollowingUsers() throws IOException {
        Type modelType = new TypeToken<List<Boolean>>(){}.getType();
        String body = TestUtils.readTestData("follow_is_following_users.json");
        List<Boolean> fixture = mGson.fromJson(body, modelType);

        final String userIds = "thelinmichael,wizzler";

        Response response = TestUtils.getResponseFromModel(fixture, modelType);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                try {
                    return ((Request) argument).getUrl().contains("type=user") &&
                            ((Request) argument).getUrl().contains("ids=" + URLEncoder.encode(userIds, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            }
        }))).thenReturn(response);

        Boolean[] result = mSpotifyService.isFollowingUsers(userIds);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldCheckFollowingArtists() throws IOException {
        Type modelType = new TypeToken<List<Boolean>>(){}.getType();
        String body = TestUtils.readTestData("follow_is_following_artists.json");
        List<Boolean> fixture = mGson.fromJson(body, modelType);

        final String artistIds = "3mOsjj1MhocRVwOejIZlTi";

        Response response = TestUtils.getResponseFromModel(fixture, modelType);

        when(mMockClient.execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object argument) {
                try {
                    return ((Request) argument).getUrl().contains("type=artist") &&
                            ((Request) argument).getUrl().contains("ids=" + URLEncoder.encode(artistIds, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            }
        }))).thenReturn(response);

        Boolean[] result = mSpotifyService.isFollowingArtists(artistIds);
        this.compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetSearchedTracks() throws IOException {
        String body = TestUtils.readTestData("search-track.json");
        TracksPager fixture = mGson.fromJson(body, TracksPager.class);

        Response response = TestUtils.getResponseFromModel(fixture, TracksPager.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        TracksPager tracks = mSpotifyService.searchTracks("Christmas");
        compareJSONWithoutNulls(body, tracks);
    }

    @Test
    public void shouldGetSearchedAlbums() throws IOException {
        String body = TestUtils.readTestData("search-album.json");
        AlbumsPager fixture = mGson.fromJson(body, AlbumsPager.class);

        Response response = TestUtils.getResponseFromModel(fixture, AlbumsPager.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        AlbumsPager result = mSpotifyService.searchAlbums("Christmas");
        compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetSearchedArtists() throws IOException {
        String body = TestUtils.readTestData("search-artist.json");
        ArtistsPager fixture = mGson.fromJson(body, ArtistsPager.class);

        Response response = TestUtils.getResponseFromModel(fixture, ArtistsPager.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        ArtistsPager result = mSpotifyService.searchArtists("Christmas");
        compareJSONWithoutNulls(body, result);
    }

    @Test
    public void shouldGetSearchedPlaylists() throws IOException {
        String body = TestUtils.readTestData("search-playlist.json");
        PlaylistsPager fixture = mGson.fromJson(body, PlaylistsPager.class);

        Response response = TestUtils.getResponseFromModel(fixture, PlaylistsPager.class);
        when(mMockClient.execute(isA(Request.class))).thenReturn(response);

        PlaylistsPager result = mSpotifyService.searchPlaylists("Christmas");
        compareJSONWithoutNulls(body, result);
    }

    /**
     * Compares the mapping fixture <-> object, ignoring NULL fields
     * This is useful to prevent issues with entities such as "Image" in
     * which width and height are not always present, and they result in
     * null values in the Image object
     *
     * @param fixture The JSON to test against
     * @param model   The object to be serialized
     */
    private <T> void compareJSONWithoutNulls(String fixture, T model) {
        JsonParser parser = new JsonParser();

        // Parsing fixture twice gets rid of nulls
        JsonElement fixtureJsonElement = parser.parse(fixture);
        String fixtureWithoutNulls = mGson.toJson(fixtureJsonElement);
        fixtureJsonElement = parser.parse(fixtureWithoutNulls);

        JsonElement modelJsonElement = mGson.toJsonTree(model);

        // We compare JsonElements from fixture
        // with the one created from model. If they're different
        // it means the model is borked
        assertEquals(fixtureJsonElement, modelJsonElement);
    }
}
