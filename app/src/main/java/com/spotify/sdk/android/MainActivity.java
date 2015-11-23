package com.spotify.sdk.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    static final String EXTRA_TOKEN = "EXTRA_TOKEN";
    private static final String KEY_CURRENT_QUERY = "CURRENT_QUERY";

    private RecyclerView mResultsList;
    private SearchView mSearchView;
    private SearchResultsAdapter mAdapter;
    private SpotifyService mSpotifyService;
    private String mCurrentQuery;
    private Player mPlayer;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayer = ((PlayerService.PlayerBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPlayer = null;
        }
    };

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentQuery = savedInstanceState.getString(KEY_CURRENT_QUERY);
        }

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mResultsList = (RecyclerView) findViewById(R.id.search_results);
        mResultsList.setHasFixedSize(true);

        Intent intent = getIntent();
        String token = intent.getStringExtra(EXTRA_TOKEN);

        initApiClient(token);
        setupSearch();
        setupPlayer();

        if (mCurrentQuery != null) {
            search(mCurrentQuery);
        }
    }

    private void initApiClient(String token) {
        SpotifyApi spotifyApi = new SpotifyApi();

        if (token != null) {
            spotifyApi.setAccessToken(token);
        } else {
            logError("No valid access token");
        }

        logMessage("Api Client created");
        mSpotifyService = spotifyApi.getService();
    }

    private void setupSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                logMessage("query text submit " + query);
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mAdapter = new SearchResultsAdapter(this, mSpotifyService, new SearchResultsAdapter.ItemSelectedListener() {
            @Override
            public void onItemSelected(View itemView, Track item) {
                handleTrackSelection(item);
                itemView.setSelected(true);
                itemView.setActivated(true);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mResultsList.setLayoutManager(layoutManager);
        mResultsList.setAdapter(mAdapter);
        mResultsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "scrolled " + dx + " " + dy);

                int itemCount = layoutManager.getItemCount();
                int itemPosition = layoutManager.findLastVisibleItemPosition();

                if (itemCount <= itemPosition) {
                    Log.d(TAG, "bottom hit");
                }
            }
        });
    }

    private void search(@NonNull String query) {
        if (!query.isEmpty()) {
            mAdapter.searchTracks(query);
            mCurrentQuery = query;
        }
    }

    private void handleTrackSelection(Track track) {
        String previewUrl = track.preview_url;

        if (previewUrl == null) {
            logMessage("Track doesn't have a preview");
            return;
        }

        if (mPlayer == null) return;

        String currentTrackUrl = mPlayer.getCurrentTrack();

        if (currentTrackUrl == null || !currentTrackUrl.equals(previewUrl)) {
            mPlayer.play(previewUrl);
        } else if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.resume();
        }
    }

    private void setupPlayer() {
        bindService(PlayerService.getIntent(this), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        startService(PlayerService.getIntent(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(PlayerService.getIntent(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCurrentQuery != null) {
            outState.putString(KEY_CURRENT_QUERY, mCurrentQuery);
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    private void logError(String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msg);
    }

    private void logMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }
}
