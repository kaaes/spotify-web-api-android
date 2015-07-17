package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class LinkedTrack implements Parcelable {
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public String type;
    public String uri;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.external_urls);
        dest.writeString(this.href);
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.uri);
    }

    public LinkedTrack() {
    }

    protected LinkedTrack(Parcel in) {
        this.external_urls = in.readHashMap(ClassLoader.getSystemClassLoader());
        this.href = in.readString();
        this.id = in.readString();
        this.type = in.readString();
        this.uri = in.readString();
    }

    public static final Parcelable.Creator<LinkedTrack> CREATOR = new Parcelable.Creator<LinkedTrack>() {
        public LinkedTrack createFromParcel(Parcel source) {
            return new LinkedTrack(source);
        }

        public LinkedTrack[] newArray(int size) {
            return new LinkedTrack[size];
        }
    };
}
