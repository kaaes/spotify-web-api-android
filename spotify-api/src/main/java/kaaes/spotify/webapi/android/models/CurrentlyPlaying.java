package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sd2_rails on 9/13/17.
 */

public class CurrentlyPlaying implements Parcelable {

    CurrentlyPlayingContext context;
    public Integer timestamp;
    public Integer progress_ms;
    public boolean is_playing;
    public Track item;

    protected CurrentlyPlaying(Parcel in) {
        context = in.readParcelable(CurrentlyPlayingContext.class.getClassLoader());
        timestamp = (Integer) in.readValue(Integer.class.getClassLoader());
        progress_ms = (Integer) in.readValue(Integer.class.getClassLoader());
        is_playing = in.readByte() != 0;
        item = in.readParcelable(Track.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(context, flags);
        dest.writeValue(timestamp);
        dest.writeValue(progress_ms);
        dest.writeByte((byte) (is_playing ? 1 : 0));
        dest.writeParcelable(item, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CurrentlyPlaying> CREATOR = new Creator<CurrentlyPlaying>() {
        @Override
        public CurrentlyPlaying createFromParcel(Parcel in) {
            return new CurrentlyPlaying(in);
        }

        @Override
        public CurrentlyPlaying[] newArray(int size) {
            return new CurrentlyPlaying[size];
        }
    };
}
