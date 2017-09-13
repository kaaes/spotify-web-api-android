package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * Created by sd2_rails on 9/13/17.
 */

public class CurrentlyPlayingContext implements Parcelable {

    public String uri;
    public String href;
    public Map<String, String> external_urls;
    public String type;

    protected CurrentlyPlayingContext(Parcel in) {
        uri = in.readString();
        href = in.readString();
        external_urls = in.readHashMap(Map.class.getClassLoader());
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uri);
        dest.writeString(href);
        dest.writeMap(this.external_urls);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CurrentlyPlayingContext> CREATOR = new Creator<CurrentlyPlayingContext>() {
        @Override
        public CurrentlyPlayingContext createFromParcel(Parcel in) {
            return new CurrentlyPlayingContext(in);
        }

        @Override
        public CurrentlyPlayingContext[] newArray(int size) {
            return new CurrentlyPlayingContext[size];
        }
    };
}
