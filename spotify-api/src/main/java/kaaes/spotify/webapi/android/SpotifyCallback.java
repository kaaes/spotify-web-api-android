package kaaes.spotify.webapi.android;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A convenience object converting {@link okhttp3.ResponseBody}s to {@link SpotifyError}s
 * in the error callbacks.
 *
 * <pre>{@code
 * spotify.getMySavedTracks(new SpotifyCallback<Pager<SavedTrack>>() {
 *     public void onResponse(Pager&lt;SavedTrack&gt; savedTrackPager) {
 *         // handle successful response
 *     }
 *
 *     public void onResponse(SpotifyError errorResponse) {
 *         // handle error response
 *     }
 *
 *     public void failure(Call<T> call, Throwable t) {
 *         // handle failure
 *     }
 * });
 * }</pre>
 *
 * @param <T> expected response type
 * @see retrofit2.Callback
 */
public abstract class SpotifyCallback<T> implements Callback<T> {

    public SpotifyCallback() {
    }

    public abstract void onResponse(SpotifyError error);
    public abstract void onResponse(Response<T> response);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful()) {
            onResponse(SpotifyError.fromRetrofitResponse(response));
        } else {
            onResponse(response);
        }
    }
}
