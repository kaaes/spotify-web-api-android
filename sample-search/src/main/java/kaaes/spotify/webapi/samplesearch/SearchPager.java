package kaaes.spotify.webapi.samplesearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit2.Call;
import retrofit2.Response;

public class SearchPager {

    private final SpotifyService mSpotifyApi;
    private int mCurrentOffset;
    private int mPageSize;
    private String mCurrentQuery;

    public interface CompleteListener {
        void onComplete(List<Track> items);
        void onError(String errorMessage);
    }

    public SearchPager(SpotifyService spotifyApi) {
        mSpotifyApi = spotifyApi;
    }

    public void getFirstPage(String query, int pageSize, CompleteListener listener) {
        mCurrentOffset = 0;
        mPageSize = pageSize;
        mCurrentQuery = query;
        getData(query, 0, pageSize, listener);
    }

    public void getNextPage(CompleteListener listener) {
        mCurrentOffset += mPageSize;
        getData(mCurrentQuery, mCurrentOffset, mPageSize, listener);
    }

    private void getData(String query, int offset, final int limit, final CompleteListener listener) {

        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.OFFSET, offset);
        options.put(SpotifyService.LIMIT, limit);

        mSpotifyApi.searchTracks(query, options).enqueue(new SpotifyCallback<TracksPager>() {

            @Override
            public void onResponse(SpotifyError error) {
                listener.onError(error.getMessage());
            }

            @Override
            public void onResponse(Response<TracksPager> response) {
                listener.onComplete(response.body().tracks.items);
            }

            @Override
            public void onFailure(Call<TracksPager> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
