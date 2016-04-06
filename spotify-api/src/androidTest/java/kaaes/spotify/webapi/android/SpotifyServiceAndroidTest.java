package kaaes.spotify.webapi.android;

import android.os.Bundle;
import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsCursorPager;
import kaaes.spotify.webapi.android.models.AudioFeature;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;

import static junit.framework.Assert.assertEquals;

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

    private Gson mGson = new GsonBuilder().create();
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

        Album album = mService.getAlbum("4Mewe6A62ZpJKmVzcaOixy");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/albums/4Mewe6A62ZpJKmVzcaOixy")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, album);
    }

    @Test
    public void getTrack() throws Exception {
        Track track = mService.getTrack("6Fer9IcKzs4G3nu0MYQmn4");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/tracks/6Fer9IcKzs4G3nu0MYQmn4")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, track);
    }

    @Test
    public void getArtist() throws Exception {
        Artist artist = mService.getArtist("54KCNI7URCrG6yjQK3Ukow");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/artists/54KCNI7URCrG6yjQK3Ukow")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, artist);
    }

    @Test
    public void getPlaylist() throws Exception {
        Playlist playlist = mService.getPlaylist("spotify", "3Jlo5JoAA9pMUQfhLaLG5u");

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/users/spotify/playlists/3Jlo5JoAA9pMUQfhLaLG5u")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, playlist);
    }

    @Test
    public void getArtistTopTracks() throws Exception {
        Tracks tracks = mService.getArtistTopTrack("54KCNI7URCrG6yjQK3Ukow", "SE");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/artists/54KCNI7URCrG6yjQK3Ukow/top-tracks?country=SE")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, tracks);
    }

    @Test
    public void getAlbumSearch() throws Exception {

        AlbumsPager search = mService.searchAlbums("XX");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/search?type=album&q=XX")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, search);
    }

    @Test
    public void getMyPlaylist() throws Exception {
        Pager<PlaylistSimple> playlist = mService.getMyPlaylists();

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/me/playlists")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, playlist);
    }

    @Test
    public void getFollowedArtists() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        final ArtistsCursorPager[] payload = {null};

        mService.getFollowedArtists(new Callback<ArtistsCursorPager>() {
            @Override
            public void success(ArtistsCursorPager artistsCursorPager, retrofit.client.Response response) {
                payload[0] = artistsCursorPager;
                latch.countDown();
            }

            @Override
            public void failure(RetrofitError error) {
                latch.countDown();
            }
        });

        latch.await(1, TimeUnit.SECONDS);

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/me/following?type=artist")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, payload[0]);
    }

    @Test
    public void getFollowedArtistsWithOptions() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        final ArtistsCursorPager[] payload = {null};

        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.LIMIT, 45);
        mService.getFollowedArtists(options, new Callback<ArtistsCursorPager>() {
            @Override
            public void success(ArtistsCursorPager artistsCursorPager, retrofit.client.Response response) {
                payload[0] = artistsCursorPager;
                latch.countDown();
            }

            @Override
            public void failure(RetrofitError error) {
                latch.countDown();
            }
        });

        latch.await(1, TimeUnit.SECONDS);

        Request request = new Request.Builder()
                .get()
                .headers(mAuthHeader)
                .url("https://api.spotify.com/v1/me/following?type=artist&limit=45")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, payload[0]);
    }

    @Test
    public void getAudioFeature() throws Exception {
        AudioFeature track = mService.getAudioFeature("6Fer9IcKzs4G3nu0MYQmn4");

        Request request = new Request.Builder()
                .get()
                .url("https://api.spotify.com/v1/audio-features/6Fer9IcKzs4G3nu0MYQmn4")
                .build();

        Response response = mClient.newCall(request).execute();
        assertEquals(200, response.code());

        compareAsJSON(response, track);
    }

    private void compareAsJSON(Response response, Object model) throws Exception {
        JsonParser parser = new JsonParser();

        JsonElement responseJsonElement = parser.parse(response.body().string());
        String fixtureWithoutNulls = mGson.toJson(responseJsonElement);
        responseJsonElement = parser.parse(fixtureWithoutNulls);

        JsonElement modelJsonElement = mGson.toJsonTree(model);

        assertEquals(responseJsonElement, modelJsonElement);
    }

}
