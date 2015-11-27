package kaaes.spotify.webapi.samplesearch;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

public class PreviewPlayer implements Player, MediaPlayer.OnCompletionListener {

    private static final String TAG = PreviewPlayer.class.getSimpleName();

    private MediaPlayer mMediaPlayer;
    private String mCurrentTrack;

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
    public void onCompletion(MediaPlayer mp) {
        release();
    }

    @Override
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

    @Override
    public void pause() {
        Log.d(TAG, "Pause");
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mCurrentTrack = null;
    }

    @Override
    public void resume() {
        Log.d(TAG, "Resume");
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    @Override
    @Nullable
    public String getCurrentTrack() {
        return mCurrentTrack;
    }

    private void createMediaPlayer(String url) throws IOException {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setDataSource(url);
        mMediaPlayer.setOnPreparedListener(new OnPreparedListener(url));
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.prepareAsync();
    }
}
