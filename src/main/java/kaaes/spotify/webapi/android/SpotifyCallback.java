package kaaes.spotify.webapi.android;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * A convenience object converting {@link retrofit.RetrofitError}s to {@link SpotifyError}s
 * in the error callbacks.
 *
 * <pre>{@code
 * spotify.getMySavedTracks(new SpotifyCallback<Pager<SavedTrack>>() {
 *     public void success(Pager&lt;SavedTrack&gt; savedTrackPager, Response response) {
 *         // handle successful response
 *     }
 *
 *     public void failure(SpotifyError error) {
 *         // handle error
 *     }
 * });
 * }</pre>
 *
 * @param <T> expected response type
 * @see retrofit.Callback
 */
public abstract class SpotifyCallback<T> implements Callback<T> {
    public abstract void failure(SpotifyError error);

    @Override
    public void failure(RetrofitError error) {
        failure(SpotifyError.fromRetrofitError(error));
    }
}
