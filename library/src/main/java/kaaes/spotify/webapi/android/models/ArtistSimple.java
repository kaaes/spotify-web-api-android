package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class ArtistSimple implements Parcelable {
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public String name;
    public String type;
    public String uri;


    public ArtistSimple() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.external_urls);
        dest.writeString(this.href);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.uri);
    }

    protected ArtistSimple(Parcel in) {
        this.external_urls = in.readHashMap(Map.class.getClassLoader());
        this.href = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.uri = in.readString();
    }

    public static final Creator<ArtistSimple> CREATOR = new Creator<ArtistSimple>() {
        public ArtistSimple createFromParcel(Parcel source) {
            return new ArtistSimple(source);
        }

        public ArtistSimple[] newArray(int size) {
            return new ArtistSimple[size];
        }
    };
}
