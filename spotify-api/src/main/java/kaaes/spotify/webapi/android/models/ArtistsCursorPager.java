package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistsCursorPager implements Parcelable {
    public CursorPager<Artist> artists;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.artists, 0);
    }

    public ArtistsCursorPager() {
    }

    protected ArtistsCursorPager(Parcel in) {
        this.artists = in.readParcelable(Pager.class.getClassLoader());
    }

    public static final Creator<ArtistsCursorPager> CREATOR = new Creator<ArtistsCursorPager>() {
        public ArtistsCursorPager createFromParcel(Parcel source) {
            return new ArtistsCursorPager(source);
        }

        public ArtistsCursorPager[] newArray(int size) {
            return new ArtistsCursorPager[size];
        }
    };
}
