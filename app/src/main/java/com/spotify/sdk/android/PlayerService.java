package com.spotify.sdk.android;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

public class PlayerService extends Service implements MediaPlayer.OnCompletionListener {

    private static final String TAG = PlayerService.class.getSimpleName();

    private final IBinder mBinder = new PlayerBinder();
    private MediaPlayer mMediaPlayer;
    private String mCurrentTrack;

    public class PlayerBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    private class OnPreparedListener implements MediaPlayer.OnPreparedListener {

        private final String mUrl;

        public OnPreparedListener(String url) {
            mUrl = url;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
            mCurrentTrack = mUrl;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.release();
        mCurrentTrack = null;
        mMediaPlayer = null;
    }

    public void play(String url) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }

        try {
            createMediaPlayer(url);
            mCurrentTrack = url;
        } catch (IOException e) {
            Log.e(TAG, "Could not play: " + url, e);
        }
    }

    private void createMediaPlayer(String url) throws IOException {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setDataSource(url);
        mMediaPlayer.setOnPreparedListener(new OnPreparedListener(url));
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.prepareAsync();
    }

    public void pause() {
        Log.d(TAG, "Pause");
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    public void resume() {
        Log.d(TAG, "Resume");
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    @Nullable
    public String getCurrentTrack() {
        return mCurrentTrack;
    }
}
