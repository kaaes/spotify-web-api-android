package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Albums implements Parcelable {
    public List<Album> albums;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(albums);
    }

    public Albums() {
    }

    protected Albums(Parcel in) {
        this.albums = in.createTypedArrayList(Album.CREATOR);
    }

    public static final Parcelable.Creator<Albums> CREATOR = new Parcelable.Creator<Albums>() {
        public Albums createFromParcel(Parcel source) {
            return new Albums(source);
        }

        public Albums[] newArray(int size) {
            return new Albums[size];
        }
    };
}
