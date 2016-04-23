package io.github.kaaes.spotify.webapi.retrofit.v1;

import io.github.kaaes.spotify.webapi.core.Config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;

/**
 * Creates and configures a REST adapter for Spotify Web API.
 *
 * Basic usage:
 * SpotifyService spotifyService = Spotify.createAuthenticatedService(accessToken);
 *
 * Access token is optional for certain endpoints
 * so if you know you'll only use the ones that don't require authorisation
 * you can use unauthenticated service:
 *
 * SpotifyService spotifyService = Spotify.createNotAuthenticatedService()
 *
 * Album album = spotifyService.getAlbum("2dIGnmEIy1WZIcZCFSj6i8");
 */
public class Spotify {

    public static final Executor HTTP_EXECUTOR = Executors.newSingleThreadExecutor();
    public static final MainThreadExecutor CALLBACK_EXECUTOR = new MainThreadExecutor();

    public static SpotifyService createAuthenticatedService(String accessToken) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Config.API_URL)
                .setRequestInterceptor(new Spotify.ApiAuthenticator(accessToken))
                .setExecutors(Spotify.HTTP_EXECUTOR, Spotify.CALLBACK_EXECUTOR)
                .build();

        return adapter.create(SpotifyService.class);
    }

    public static SpotifyService createNotAuthenticatedService() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Config.API_URL)
                .setExecutors(Spotify.HTTP_EXECUTOR, Spotify.CALLBACK_EXECUTOR)
                .build();

        return adapter.create(SpotifyService.class);
    }

    /**
     * The request interceptor that will add the header with OAuth
     * token to every request made with the wrapper.
     */
    public static class ApiAuthenticator  implements RequestInterceptor {

        private String mAccessToken;

        public ApiAuthenticator(String accessToken) {
            mAccessToken = accessToken;
        }

        @Override
        public void intercept(RequestFacade request) {
            if (mAccessToken != null) {
                request.addHeader("Authorization", "Bearer " + mAccessToken);
            }
        }
    }
}
