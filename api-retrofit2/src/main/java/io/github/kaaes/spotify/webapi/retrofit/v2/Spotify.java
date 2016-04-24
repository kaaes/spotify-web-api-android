package io.github.kaaes.spotify.webapi.retrofit.v2;

import java.io.IOException;

import io.github.kaaes.spotify.webapi.core.Config;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
 * Call<Album> call = spotifyService.getAlbum("2dIGnmEIy1WZIcZCFSj6i8");
 * Response<Album> response = call.execute();
 * Album album = response.body();
 */
public class Spotify {

    public static SpotifyService createAuthenticatedService(String accessToken) {

        Retrofit build = new Retrofit.Builder()
                .client(createHttpClient(accessToken))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Config.API_URL)
                .build();

        return build.create(SpotifyService.class);
    }

    public static SpotifyService createNotAuthenticatedService() {

        Retrofit build = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Config.API_URL)
                .build();

        return build.create(SpotifyService.class);
    }


    public static OkHttpClient createHttpClient(String accessToken) {
        return new OkHttpClient.Builder()
                .addInterceptor(new ApiAuthenticator(accessToken))
                .build();
    }


    /**
     * The request interceptor that will add the header with OAuth
     * token to every request made with the wrapper.
     */
    public static class ApiAuthenticator implements Interceptor {

        private String mAccessToken;

        public ApiAuthenticator(String accessToken) {
            mAccessToken = accessToken;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (mAccessToken != null) {
                Request authRequest = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + mAccessToken)
                        .build();
                return chain.proceed(authRequest);
            }
            return chain.proceed(request);
        }
    }
}
