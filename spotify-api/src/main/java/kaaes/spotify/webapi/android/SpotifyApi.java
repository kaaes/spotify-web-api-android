package kaaes.spotify.webapi.android;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Creates and configures a REST adapter for Spotify Web API.
 *
 * Basic usage:
 * SpotifyApi wrapper = new SpotifyApi();
 *
 * Setting access token is optional for certain endpoints
 * so if you know you'll only use the ones that don't require authorisation
 * you can skip this step:
 * wrapper.setAccessToken(authenticationResponse.getAccessToken());
 *
 * SpotifyService spotify = wrapper.getService();
 *
 * Album album = spotify.getAlbum("2dIGnmEIy1WZIcZCFSj6i8");
 */
public class SpotifyApi {

    /**
     * Main Spotify Web API endpoint
     */
    public static final String SPOTIFY_WEB_API_ENDPOINT = "https://api.spotify.com/v1/";

    /**
     * The request interceptor that will add the header with OAuth
     * token to every request made with the wrapper.
     */
    private class WebApiAuthenticator implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (mAccessToken != null) {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.addHeader("Authorization", "Bearer " + mAccessToken);
                request = requestBuilder.build();
            }
            return chain.proceed(request);
        }
    }

    private final SpotifyService mSpotifyService;

    private String mAccessToken;

    /**
     * Create instance of SpotifyApi with given executors.
     *
     * @param client the okhttp3 client retrofit will use. Can be null.
     * @param callbackExecutor executor for callbacks. If null is passed than the same
     *                         thread that created the instance is used.
     */
    public SpotifyApi(OkHttpClient client, Executor callbackExecutor) {
        mSpotifyService = init(client, callbackExecutor);
    }

    private SpotifyService init(OkHttpClient client, Executor callbackExecutor) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder clientBuilder = client.newBuilder();
        clientBuilder.networkInterceptors().add(new WebApiAuthenticator());
        clientBuilder.interceptors().add(loggingInterceptor);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SPOTIFY_WEB_API_ENDPOINT)
                .client(clientBuilder.build());

        if (null != callbackExecutor) {
            builder.callbackExecutor(callbackExecutor);
        }
        final Retrofit retrofit = builder.build();
        return retrofit.create(SpotifyService.class);
    }

    /**
     *  New instance of SpotifyApi,
     *  with single thread executor both for http and callbacks.
     */
    public SpotifyApi() {
        mSpotifyService = init(new OkHttpClient(), null);
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
    public SpotifyService getService() {
        return mSpotifyService;
    }
}
