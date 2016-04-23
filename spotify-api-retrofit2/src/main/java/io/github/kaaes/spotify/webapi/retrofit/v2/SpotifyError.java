package io.github.kaaes.spotify.webapi.retrofit.v2;

import java.io.IOException;

import retrofit2.Response;

public class SpotifyError extends Exception {

    public static <T> SpotifyError fromResponse(Response<T> response) {

        String error = null;
        try {
            error = response.errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (error != null) {
            return new SpotifyError(error);
        } else {
            return new SpotifyError(new Throwable("Can't read error response"));
        }
    }

    public SpotifyError(String detailMessage) {
        super(detailMessage);
    }

    public SpotifyError(Throwable cause) {
        super(cause);
    }
}
