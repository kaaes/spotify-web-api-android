package io.github.kaaes.spotify.webapi.samplesearch;

import io.github.kaaes.spotify.webapi.core.Options;
import io.github.kaaes.spotify.webapi.core.models.Track;
import io.github.kaaes.spotify.webapi.core.models.TracksPager;
import io.github.kaaes.spotify.webapi.retrofit.v2.SpotifyCallback;
import io.github.kaaes.spotify.webapi.retrofit.v2.SpotifyError;
import io.github.kaaes.spotify.webapi.retrofit.v2.SpotifyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class SearchPager {

    private final SpotifyService mSpotifyApi;
    private int mCurrentOffset;
    private int mPageSize;
    private String mCurrentQuery;

    public interface CompleteListener {
        void onComplete(List<Track> items);

        void onError(Throwable error);
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
        options.put(Options.OFFSET, offset);
        options.put(Options.LIMIT, limit);

        Call<TracksPager> call = mSpotifyApi.searchTracks(query, options);
        call.enqueue(new SpotifyCallback<TracksPager>() {
            @Override
            public void onResponse(Call<TracksPager> call, Response<TracksPager> response, TracksPager payload) {
                listener.onComplete(payload.tracks.items);
            }

            @Override
            public void onFailure(Call<TracksPager> call, SpotifyError error) {
                listener.onError(error);
            }
        });
    }
}
