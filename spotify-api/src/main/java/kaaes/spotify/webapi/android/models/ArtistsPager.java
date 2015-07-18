package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistsPager implements Parcelable {
    public Pager<Artist> artists;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.artists, 0);
    }

    public ArtistsPager() {
    }

    protected ArtistsPager(Parcel in) {
        this.artists = in.readParcelable(Pager.class.getClassLoader());
    }

    public static final Parcelable.Creator<ArtistsPager> CREATOR = new Parcelable.Creator<ArtistsPager>() {
        public ArtistsPager createFromParcel(Parcel source) {
            return new ArtistsPager(source);
        }

        public ArtistsPager[] newArray(int size) {
            return new ArtistsPager[size];
        }
    };
}
