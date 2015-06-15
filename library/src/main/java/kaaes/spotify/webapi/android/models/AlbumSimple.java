package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

public class AlbumSimple implements Parcelable {
    public String album_type;
    public List<String> available_markets;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public List<Image> images;
    public String name;
    public String type;
    public String uri;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.album_type);
        dest.writeStringList(this.available_markets);
        dest.writeMap(this.external_urls);
        dest.writeString(this.href);
        dest.writeString(this.id);
        dest.writeTypedList(images);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.uri);
    }

    public AlbumSimple() {
    }

    protected AlbumSimple(Parcel in) {
        this.album_type = in.readString();
        this.available_markets = in.createStringArrayList();
        this.external_urls = in.readHashMap(ClassLoader.getSystemClassLoader());
        this.href = in.readString();
        this.id = in.readString();
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.name = in.readString();
        this.type = in.readString();
        this.uri = in.readString();
    }

    public static final Parcelable.Creator<AlbumSimple> CREATOR = new Parcelable.Creator<AlbumSimple>() {
        public AlbumSimple createFromParcel(Parcel source) {
            return new AlbumSimple(source);
        }

        public AlbumSimple[] newArray(int size) {
            return new AlbumSimple[size];
        }
    };
}
