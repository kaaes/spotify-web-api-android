package kaaes.spotify.webapi.android;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.skyscreamer.jsonassert.JSONParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Albums;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.FeaturedPlaylists;
import kaaes.spotify.webapi.android.models.NewReleases;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.User;
import kaaes.spotify.webapi.android.models.UserSimple;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

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

        Response response = TestUtils.getResponseFromModel(fixture);
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

        Response response = TestUtils.getResponseFromModel(fixture);
        when(mMockClient.execute(argThat(new MatchesId(ids)))).thenReturn(response);

        Tracks tracks = mSpotifyService.getTracks(ids);
        this.compareJSONWithoutNulls(body, tracks);
    }

    @Test
    public void shouldGetAlbumData() throws IOException {
        String body = TestUtils.readTestData("album.json");
        Album fixture = mGson.fromJson(body, Album.class);

        Response response = TestUtils.getResponseFromModel(fixture);
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

        Response response = TestUtils.getResponseFromModel(fixture);
        when(mMockClient.execute(argThat(new MatchesId(ids)))).thenReturn(response);

        Albums albums = mSpotifyService.getAlbums(ids);
        this.compareJSONWithoutNulls(body, albums);
    }

    @Test
    public void shouldGetArtistData() throws IOException {
        String body = TestUtils.readTestData("artist.json");
        Artist fixture = mGson.fromJson(body, Artist.class);

        Response response = TestUtils.getResponseFromModel(fixture);
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

        Response response = TestUtils.getResponseFromModel(fixture);
        when(mMockClient.execute(argThat(new MatchesId(ids)))).thenReturn(response);

        Artists artists = mSpotifyService.getArtists(ids);

        this.compareJSONWithoutNulls(body, artists);
    }

    @Test
    @Ignore
    public void shouldGetArtistsAlbumsData() throws IOException {
        String artistId = "1vCWHaC5f2uS3yhpwWbIA6";
        String body = TestUtils.readTestData("artist-album.json");
        Pager<Album> fixture = mGson.fromJson(body, Pager.class);

        Response response = TestUtils.getResponseFromModel(fixture);
        when(mMockClient.execute(argThat(new MatchesId(artistId)))).thenReturn(response);

        Pager<Album> albums = mSpotifyService.getArtistAlbums(artistId);

        this.compareJSONWithoutNulls(body, albums);
    }

    @Test
    public void shouldGetNewReleases() throws IOException {
        final String countryId = "SE";
        final int limit = 5;

        String body = TestUtils.readTestData("new-releases.json");
        NewReleases fixture = mGson.fromJson(body, NewReleases.class);

        Response response = TestUtils.getResponseFromModel(fixture);

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

        Response response = TestUtils.getResponseFromModel(fixture);

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

        Response response = TestUtils.getResponseFromModel(fixture);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        UserSimple userSimple = mSpotifyService.getUser(fixture.id);
        this.compareJSONWithoutNulls(body, userSimple);
    }

    @Test
    public void shouldGetCurrentUserData() throws IOException {
        String body = TestUtils.readTestData("current-user.json");
        User fixture = mGson.fromJson(body, User.class);

        Response response = TestUtils.getResponseFromModel(fixture);
        when(mMockClient.execute(Matchers.<Request> any())).thenReturn(response);

        User user = mSpotifyService.getMe();
        this.compareJSONWithoutNulls(body, user);
    }

    @Test
    public void shouldCheckFollowingUsers() throws IOException {
        String body = TestUtils.readTestData("follow_is_following_users.json");
        List<Boolean> fixture = mGson.fromJson(body, List.class);

        final String userIds = "thelinmichael,wizzler";

        Response response = TestUtils.getResponseFromModel(fixture);

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
        String body = TestUtils.readTestData("follow_is_following_artists.json");
        List<Boolean> fixture = mGson.fromJson(body, List.class);

        final String artistIds = "3mOsjj1MhocRVwOejIZlTi";

        Response response = TestUtils.getResponseFromModel(fixture);

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

    /**
     * Compares the mapping fixture <-> object, ignoring NULL fields
     * This is useful to prevent issues with entities such as "Image" in
     * which width and height are not always present, and they result in
     * null values in the Image object
     * @todo: replace with proper comparator, since integers are being
     * serialized as floats
     * @param fixture The JSON to test against
     * @param object The object to be serialized
     */
    private void compareJSONWithoutNulls(String fixture, Object object) {
        String json = mGson.toJson(object);
        Object cleanBody = mGson.fromJson(fixture, Object.class);
        String cleanBodyJson = mGson.toJson(cleanBody);

        JsonParser parser = new JsonParser();
        JsonElement o1 = parser.parse(cleanBodyJson);
        JsonElement o2 = parser.parse(json);
        assertEquals(o1, o2);
    }
}
