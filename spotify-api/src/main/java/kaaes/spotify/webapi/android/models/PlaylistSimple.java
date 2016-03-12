package kaaes.spotify.webapi.android.models;

import android.os.Parcel;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-object-simplified">Playlist object model (simplified)</a>
 */
public class PlaylistSimple extends PlaylistBase {
    public PlaylistTracksInformation tracks;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.tracks, flags);
    }

    public PlaylistSimple() {
    }

    protected PlaylistSimple(Parcel in) {
        super(in);
        this.tracks = in.readParcelable(PlaylistTracksInformation.class.getClassLoader());
    }

    public static final Creator<PlaylistSimple> CREATOR = new Creator<PlaylistSimple>() {
        public PlaylistSimple createFromParcel(Parcel source) {
            return new PlaylistSimple(source);
        }

        public PlaylistSimple[] newArray(int size) {
            return new PlaylistSimple[size];
        }
    };
}
