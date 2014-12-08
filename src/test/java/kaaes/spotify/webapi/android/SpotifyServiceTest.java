package kaaes.spotify.webapi.android;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatcher;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

import static org.junit.Assert.assertThat;
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
        mGson = new GsonBuilder().serializeNulls().create();
    }

    @Test
    public void shouldGetTrackData() throws IOException {
        String body = TestUtils.readTestData("track.json");
        Track fixture = mGson.fromJson(body, Track.class);

        Response response = TestUtils.getResponseFromModel(fixture);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        Track track = mSpotifyService.getTrack(fixture.id);
        String json = mGson.toJson(track);

        assertThat(body, sameJSONAs(json));
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
        String json = mGson.toJson(tracks);

        assertThat(body, sameJSONAs(json));
    }

    @Test
    public void shouldGetAlbumData() throws IOException {
        String body = TestUtils.readTestData("album.json");
        Album fixture = mGson.fromJson(body, Album.class);

        Response response = TestUtils.getResponseFromModel(fixture);
        when(mMockClient.execute(argThat(new MatchesId(fixture.id)))).thenReturn(response);

        Album album = mSpotifyService.getAlbum(fixture.id);
        String json = mGson.toJson(album);

        assertThat(body, sameJSONAs(json));
    }
}
