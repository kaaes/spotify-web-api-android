package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NewReleases implements Parcelable {
    public Pager<AlbumSimple> albums;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.albums, 0);
    }

    public NewReleases() {
    }

    protected NewReleases(Parcel in) {
        this.albums = in.readParcelable(Pager.class.getClassLoader());
    }

    public static final Parcelable.Creator<NewReleases> CREATOR = new Parcelable.Creator<NewReleases>() {
        public NewReleases createFromParcel(Parcel source) {
            return new NewReleases(source);
        }

        public NewReleases[] newArray(int size) {
            return new NewReleases[size];
        }
    };
}
