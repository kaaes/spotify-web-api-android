package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaylistsPager implements Parcelable {
    public Pager<PlaylistSimple> playlists;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.playlists, 0);
    }

    public PlaylistsPager() {
    }

    protected PlaylistsPager(Parcel in) {
        this.playlists = in.readParcelable(Pager.class.getClassLoader());
    }

    public static final Parcelable.Creator<PlaylistsPager> CREATOR = new Parcelable.Creator<PlaylistsPager>() {
        public PlaylistsPager createFromParcel(Parcel source) {
            return new PlaylistsPager(source);
        }

        public PlaylistsPager[] newArray(int size) {
            return new PlaylistsPager[size];
        }
    };
}
