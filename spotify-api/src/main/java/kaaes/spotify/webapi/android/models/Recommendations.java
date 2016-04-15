package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recommendations implements Parcelable {

    public List<Seed> seeds;

    public List<Track> tracks;

    public Recommendations() {
    }

    protected Recommendations(Parcel in) {
        seeds = in.createTypedArrayList(Seed.CREATOR);
        tracks = in.createTypedArrayList(Track.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(seeds);
        dest.writeTypedList(tracks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recommendations> CREATOR = new Creator<Recommendations>() {
        @Override
        public Recommendations createFromParcel(Parcel in) {
            return new Recommendations(in);
        }

        @Override
        public Recommendations[] newArray(int size) {
            return new Recommendations[size];
        }
    };
}
