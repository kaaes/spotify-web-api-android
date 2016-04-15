package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SeedsGenres implements Parcelable {

    public List<String> genres;

    public SeedsGenres() {
    }

    protected SeedsGenres(Parcel in) {
        genres = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(genres);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SeedsGenres> CREATOR = new Creator<SeedsGenres>() {
        @Override
        public SeedsGenres createFromParcel(Parcel in) {
            return new SeedsGenres(in);
        }

        @Override
        public SeedsGenres[] newArray(int size) {
            return new SeedsGenres[size];
        }
    };
}
