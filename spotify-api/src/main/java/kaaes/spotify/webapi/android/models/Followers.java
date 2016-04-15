package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#followers-object">Followers</a>
 */
public class Followers implements Parcelable {
    public String href;
    public Integer total;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeInt(this.total);
    }

    public Followers() {
    }

    protected Followers(Parcel in) {
        this.href = in.readString();
        this.total = in.readInt();
    }

    public static final Parcelable.Creator<Followers> CREATOR = new Parcelable.Creator<Followers>() {
        public Followers createFromParcel(Parcel source) {
            return new Followers(source);
        }

        public Followers[] newArray(int size) {
            return new Followers[size];
        }
    };
}
