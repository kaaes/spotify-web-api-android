package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-object-full">Playlist object model</a>
 */
public class Playlist extends PlaylistBase {
    public String description;
    public Followers followers;
    public Pager<PlaylistTrack> tracks;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.description);
        dest.writeParcelable(this.followers, 0);
        dest.writeParcelable(this.tracks, 0);
    }

    public Playlist() {
    }

    protected Playlist(Parcel in) {
        super(in);
        this.description = in.readString();
        this.followers = in.readParcelable(Followers.class.getClassLoader());
        this.tracks = in.readParcelable(Pager.class.getClassLoader());
    }

    public static final Parcelable.Creator<Playlist> CREATOR = new Parcelable.Creator<Playlist>() {
        public Playlist createFromParcel(Parcel source) {
            return new Playlist(source);
        }

        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };
}