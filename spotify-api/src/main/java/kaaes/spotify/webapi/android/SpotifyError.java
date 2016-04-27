package kaaes.spotify.webapi.android;

import kaaes.spotify.webapi.android.models.ErrorDetails;
import kaaes.spotify.webapi.android.models.ErrorResponse;

/**
 * This object wraps error responses from the Web API
 * and provides access to details returned by the request that are usually more
 * descriptive than default Retrofit error messages.
 * <p>
 * To use with asynchronous requests pass {@link SpotifyCallback}
 * instead of {@link retrofit2.Callback} when making the request:
 * <pre>{@code
 * spotify.getMySavedTracks(new SpotifyCallback<Pager<SavedTrack>>() {
 *      public void onResponse(Pager&lt;SavedTrack&gt; savedTrackPager) {
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
 * <p>
 * To use with synchronous requests:
 * <pre>{@code
 * try {
 *     Pager<SavedTrack> mySavedTracks = spotify.getMySavedTracks();
 * } catch (RetrofitError error) {
 *     SpotifyError spotifyError = SpotifyError.fromRetrofitError(error);
 * }
 * }</pre>
 */
public class SpotifyError extends Exception {

    private final ErrorDetails mErrorDetails;

    public static SpotifyError fromRetrofitError(ErrorResponse errorResponse) {
        if (errorResponse != null && errorResponse.error != null) {
            String message = errorResponse.error.status + " " + errorResponse.error.message;
            return new SpotifyError(errorResponse.error, message);
        } else {
            return new SpotifyError();
        }
    }

    public SpotifyError(ErrorDetails errorDetails, String message) {
        super(message);
        mErrorDetails = errorDetails;
    }

    public SpotifyError() {
        super();
        mErrorDetails = null;
    }

    /**
     * @return true if there are {@link kaaes.spotify.webapi.android.models.ErrorDetails}
     * associated with this error. False otherwise.
     */
    public boolean hasErrorDetails() {
        return mErrorDetails != null;
    }

    /**
     * @return Details returned from the Web API associated with this error if present.
     */
    public ErrorDetails getErrorDetails() {
        return mErrorDetails;
    }
}
