package kaaes.spotify.webapi.android;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * A convenience object converting {@link retrofit.RetrofitError}s to {@link SpotifyError}s
 * in the error callbacks.
 *
 * {code <pre>
 * spotify.getMySavedTracks(new SpotifyCallback&lt;Pager&lt;SavedTrack&gt;&gt;() {
 *     {@literal @}Override
 *     public void success(Pager&lt;SavedTrack&gt; savedTrackPager, Response response) {
 *         // handle successful response
 *     }
 *
 *     {@literal @}Override
 *     public void failure(SpotifyError error) {
 *         // handle error
 *     }
 * });
 * </pre>}
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
