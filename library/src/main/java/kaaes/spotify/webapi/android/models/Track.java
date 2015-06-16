package kaaes.spotify.webapi.android.models;

import android.os.Parcel;

import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#track-object-full">Track object model</a>
 */
public class Track extends TrackSimple {
    public AlbumSimple album;
    public Map<String, String> external_ids;
    public Integer popularity;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.album, 0);
        dest.writeMap(this.external_ids);
        dest.writeValue(this.popularity);
    }

    public Track() {
    }

    protected Track(Parcel in) {
        super(in);
        this.album = in.readParcelable(AlbumSimple.class.getClassLoader());
        this.external_ids = in.readHashMap(Map.class.getClassLoader());
        this.popularity = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}