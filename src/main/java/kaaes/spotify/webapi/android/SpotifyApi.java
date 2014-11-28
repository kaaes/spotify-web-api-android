package kaaes.spotify.webapi.android;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Creates and configures a REST adapter for Spotify Web API.
 *
 * Basic usage:
 * SpotifyApiWrapper wrapper = new SpotifyApiWrapper(CLIENT_ID);
 *
 * Setting access token is optional for certain endpoints
 * so if you know you'll only use the ones that don't require authorisation
 * you can skip this step:
 * wrapper.setAccessToken(authenticationResponse.getAccessToken());
 *
 * SpotifyApi api = wrapper.getApi();
 *
 * api.getAlbum("2dIGnmEIy1WZIcZCFSj6i8", new Callback<Album>() {
 * public void success(Album album, Response response) {
 * Log.d("Album success", album.toString());
 * }
 *
 * public void failure(RetrofitError error) {
 * Log.d("Album failure", error.toString());
 * }
 * });
 */
public class SpotifyApi {

    /**
     * Main Spotify Web API endpoint
     */
    public static final String SPOTIFY_WEB_API_ENDPOINT = "https://api.spotify.com/v1";

    /**
     * The request interceptor that will add the header with OAuth
     * token to every request made with the wrapper.
     */
    public class WebApiAuthenticator implements RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            if (mAccessToken != null) {
                request.addHeader("Authorization", "Bearer " + mAccessToken);
            }
        }
    }

    private final SpotifyService mSpotifyService;

    private String mAccessToken;

    public SpotifyApi() {
        Executor executor = Executors.newSingleThreadExecutor();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setClient(new OkClient(new OkHttpClient()))
                .setExecutors(executor, executor)
                .setEndpoint(SPOTIFY_WEB_API_ENDPOINT)
                .setRequestInterceptor(new WebApiAuthenticator())
                .build();

        mSpotifyService = restAdapter.create(SpotifyService.class);
    }

    /**
     * Sets access token on the wrapper.
     * Use to set or update token with the new value.
     * If you want to remove token set it to null.
     *
     * @param accessToken The token to set on the wrapper.
     * @return The instance of the wrapper.
     */
    public SpotifyApi setAccessToken(String accessToken) {
        mAccessToken = accessToken;
        return this;
    }

    /**
     * @return The SpotifyApi instance
     */
    public SpotifyService getApi() {
        return mSpotifyService;
    }
}
