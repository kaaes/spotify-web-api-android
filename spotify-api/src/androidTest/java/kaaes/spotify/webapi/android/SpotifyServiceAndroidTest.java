package kaaes.spotify.webapi.android;

import android.os.Bundle;
import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsCursorPager;
import kaaes.spotify.webapi.android.models.AudioFeaturesTrack;
import kaaes.spotify.webapi.android.models.AudioFeaturesTracks;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.Recommendations;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * These are functional tests that make actual requests to the Spotify Web API endpoints and
 * compare raw JSON responses with the ones received with the interface crated by this library.
 * They require an access token to run, which is currently pretty manual and annoying but hopefully
 * we'll solve that in the future.
 *
 * Running the tests:
 * ./gradlew :spotify-api:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.access_token=valid_access_token
 */
public class SpotifyServiceAndroidTest {

    private OkHttpClient mClient = new OkHttpClient();
    private SpotifyService mService;
    private Headers mAuthHeader;

    @Before
    public void setUp() throws Exception {
        Bundle arguments = InstrumentationRegistry.getArguments();
        String accessToken = arguments.getString("access_token");
        if (accessToken == null) {
            Assert.fail("Access token can't be null");
        }

        SpotifyApi spotifyApi = new SpotifyApi();
        spotifyApi.setAccessToken(accessToken);
        mService = spotifyApi.getService();

        mAuthHeader = Headers.of("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void getAlbum() throws Exception {

        Album payload = mService.getAlbum("4Mewe6A62ZpJKmVzcaOixy");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/albums/4Mewe6A62ZpJKmVzcaOixy")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getTrack() throws Exception {
        Track payload = mService.getTrack("6Fer9IcKzs4G3nu0MYQmn4");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/tracks/6Fer9IcKzs4G3nu0MYQmn4")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getArtist() throws Exception {
        Artist payload = mService.getArtist("54KCNI7URCrG6yjQK3Ukow");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/artists/54KCNI7URCrG6yjQK3Ukow")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getPlaylist() throws Exception {
        Playlist payload = mService.getPlaylist("spotify", "3Jlo5JoAA9pMUQfhLaLG5u");

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/users/spotify/playlists/3Jlo5JoAA9pMUQfhLaLG5u")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getArtistTopTracks() throws Exception {
        Tracks payload = mService.getArtistTopTrack("54KCNI7URCrG6yjQK3Ukow", "SE");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/artists/54KCNI7URCrG6yjQK3Ukow/top-tracks?country=SE")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getAlbumSearch() throws Exception {

        AlbumsPager payload = mService.searchAlbums("XX");

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/search?type=album&q=XX")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getMyPlaylist() throws Exception {
        Pager<PlaylistSimple> payload = mService.getMyPlaylists();

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/me/playlists")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getFollowedArtists() throws Exception {

        ArtistsCursorPager payload = mService.getFollowedArtists();

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/me/following?type=artist")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getFollowedArtistsWithOptions() throws Exception {
        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.LIMIT, 45);
        ArtistsCursorPager payload = mService.getFollowedArtists(options);

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/me/following?type=artist&limit=45")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Ignore("Need a better way to compare the json - current doesn't really work on floats")
    @Test
    public void getAudioFeaturesForTrack() throws Exception {
        AudioFeaturesTrack payload = mService.getTrackAudioFeatures("6Fer9IcKzs4G3nu0MYQmn4");

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/audio-features/6Fer9IcKzs4G3nu0MYQmn4")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Ignore("Need a better way to compare the json - current doesn't really work on floats")
    @Test
    public void getAudioFeaturesForTracks() throws Exception {
        AudioFeaturesTracks payload = mService.getTracksAudioFeatures("6Fer9IcKzs4G3nu0MYQmn4,24NwBd5vZ2CK8VOQVnqdxr,7cy1bEJV6FCtDaYpsk8aG6");

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/audio-features?ids=6Fer9IcKzs4G3nu0MYQmn4,24NwBd5vZ2CK8VOQVnqdxr,7cy1bEJV6FCtDaYpsk8aG6")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getUserTopArtists() throws Exception {
        Pager<Artist> payload = mService.getTopArtists();

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/me/top/artists")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    @Test
    public void getUserTopTracks() throws Exception {
        Pager<Track> payload = mService.getTopTracks();

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/me/top/tracks")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }


    @Ignore("something fishy at this endpoint, the results change between requests")
    @Test
    public void getRecommendations() throws Exception {
        Map<String, Object> options = new HashMap<>();
        options.put("seed_artists", "4cJKxS7uOPhwb5UQ70sYpN,6UUrUCIZtQeOf8tC0WuzRy");
        Recommendations payload = mService.getRecommendations(options);

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/recommendations?seed_artists=4cJKxS7uOPhwb5UQ70sYpN,6UUrUCIZtQeOf8tC0WuzRy")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        assertThat(payload, JsonEquals.jsonEquals(response.body().string()));
    }

    private static class JsonEquals extends BaseMatcher<Object> {

        private static Gson sGson = new GsonBuilder().create();
        private final JsonElement mExpectedJsonElement;

        public JsonEquals(String expected) {
            mExpectedJsonElement = sGson.fromJson(expected, JsonElement.class);
        }

        @Override
        public boolean matches(Object actualRaw) {
            JsonElement actualJsonElement = sGson.toJsonTree(actualRaw);
            String withoutNulls = sGson.toJson(mExpectedJsonElement);
            JsonElement expectedJsonElementWithoutNulls = sGson.fromJson(withoutNulls, JsonElement.class);
            return expectedJsonElementWithoutNulls.equals(actualJsonElement);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(mExpectedJsonElement.toString());
        }

        static JsonEquals jsonEquals(String expected) {
            return new JsonEquals(expected);
        }
    }
}
