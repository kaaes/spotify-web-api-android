package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Seed implements Parcelable {

    public int afterFilteringSize;
    public int afterRelinkingSize;
    public String href;
    public String id;
    public int initialPoolSize;
    public String type;

    public Seed() {
    }

    protected Seed(Parcel in) {
        afterFilteringSize = in.readInt();
        afterRelinkingSize = in.readInt();
        href = in.readString();
        id = in.readString();
        initialPoolSize = in.readInt();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(afterFilteringSize);
        dest.writeInt(afterRelinkingSize);
        dest.writeString(href);
        dest.writeString(id);
        dest.writeInt(initialPoolSize);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Seed> CREATOR = new Creator<Seed>() {
        @Override
        public Seed createFromParcel(Parcel in) {
            return new Seed(in);
        }

        @Override
        public Seed[] newArray(int size) {
            return new Seed[size];
        }
    };
}
