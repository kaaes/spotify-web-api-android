package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TracksToRemove implements Parcelable {
    public List<TrackToRemove> tracks;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.tracks);
    }

    public TracksToRemove() {
    }

    protected TracksToRemove(Parcel in) {
        this.tracks = new ArrayList<TrackToRemove>();
        in.readList(this.tracks, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<TracksToRemove> CREATOR = new Parcelable.Creator<TracksToRemove>() {
        public TracksToRemove createFromParcel(Parcel source) {
            return new TracksToRemove(source);
        }

        public TracksToRemove[] newArray(int size) {
            return new TracksToRemove[size];
        }
    };
}
