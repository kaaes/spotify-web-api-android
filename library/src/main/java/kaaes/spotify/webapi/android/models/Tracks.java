package kaaes.spotify.webapi.android.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Tracks implements Parcelable {
    public List<Track> tracks;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tracks);
    }

    public Tracks() {
    }

    protected Tracks(Parcel in) {
        this.tracks = in.createTypedArrayList(Track.CREATOR);
    }

    public static final Parcelable.Creator<Tracks> CREATOR = new Parcelable.Creator<Tracks>() {
        public Tracks createFromParcel(Parcel source) {
            return new Tracks(source);
        }

        public Tracks[] newArray(int size) {
            return new Tracks[size];
        }
    };
}
