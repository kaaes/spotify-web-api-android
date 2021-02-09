package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayHistory implements Parcelable {
    public ContextObject context;
    public String playedAt;
    public TrackSimple track;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.context, flags);
        dest.writeString(this.playedAt);
        dest.writeParcelable(this.track, flags);
    }

    public PlayHistory(){

    }

    protected PlayHistory(Parcel in){
        this.context = in.readParcelable(ContextObject.class.getClassLoader());
        this.playedAt = in.readString();
        this.track = in.readParcelable(TrackSimple.class.getClassLoader());
    }

    public static final Creator<PlayHistory> CREATOR = new Creator<PlayHistory>() {
        public PlayHistory createFromParcel(Parcel source) {
            return new PlayHistory(source);
        }

        public PlayHistory[] newArray(int size) {
            return new PlayHistory[size];
        }
    };
}
