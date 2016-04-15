package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AudioFeaturesTracks implements Parcelable {

    public List<AudioFeaturesTrack> audio_features;

    public AudioFeaturesTracks() {
    }

    protected AudioFeaturesTracks(Parcel in) {
        audio_features = in.createTypedArrayList(AudioFeaturesTrack.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeTypedList(audio_features);
    }

    public static final Creator<AudioFeaturesTracks> CREATOR = new Creator<AudioFeaturesTracks>() {
        @Override
        public AudioFeaturesTracks createFromParcel(Parcel in) {
            return new AudioFeaturesTracks(in);
        }

        @Override
        public AudioFeaturesTracks[] newArray(int size) {
            return new AudioFeaturesTracks[size];
        }
    };
}
