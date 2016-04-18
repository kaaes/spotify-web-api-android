package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AudioFeatures implements Parcelable {
    public List<AudioFeature> audio_features;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(audio_features);
    }

    public AudioFeatures() {
    }

    protected AudioFeatures(Parcel in) {
        this.audio_features = in.createTypedArrayList(AudioFeature.CREATOR);
    }

    public static final Creator<AudioFeatures> CREATOR = new Creator<AudioFeatures>() {
        public AudioFeatures createFromParcel(Parcel source) {
            return new AudioFeatures(source);
        }

        public AudioFeatures[] newArray(int size) {
            return new AudioFeatures[size];
        }
    };
}
