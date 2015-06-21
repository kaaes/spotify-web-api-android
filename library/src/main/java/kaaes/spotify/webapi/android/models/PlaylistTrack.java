package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-track-object">Playlist track object model</a>
 */
public class PlaylistTrack implements Parcelable {
    public String added_at;
    public UserPublic added_by;
    public Track track;
    public Boolean is_local;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.added_at);
        dest.writeParcelable(this.added_by, flags);
        dest.writeParcelable(this.track, 0);
        dest.writeValue(this.is_local);
    }

    public PlaylistTrack() {
    }

    protected PlaylistTrack(Parcel in) {
        this.added_at = in.readString();
        this.added_by = in.readParcelable(UserPublic.class.getClassLoader());
        this.track = in.readParcelable(Track.class.getClassLoader());
        this.is_local = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PlaylistTrack> CREATOR = new Parcelable.Creator<PlaylistTrack>() {
        public PlaylistTrack createFromParcel(Parcel source) {
            return new PlaylistTrack(source);
        }

        public PlaylistTrack[] newArray(int size) {
            return new PlaylistTrack[size];
        }
    };
}
