package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TrackToRemoveWithPosition implements Parcelable {
    public String uri;
    public List<Integer> positions;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
        dest.writeList(this.positions);
    }

    public TrackToRemoveWithPosition() {
    }

    protected TrackToRemoveWithPosition(Parcel in) {
        this.uri = in.readString();
        this.positions = new ArrayList<Integer>();
        in.readList(this.positions, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<TrackToRemoveWithPosition> CREATOR = new Parcelable.Creator<TrackToRemoveWithPosition>() {
        public TrackToRemoveWithPosition createFromParcel(Parcel source) {
            return new TrackToRemoveWithPosition(source);
        }

        public TrackToRemoveWithPosition[] newArray(int size) {
            return new TrackToRemoveWithPosition[size];
        }
    };
}
