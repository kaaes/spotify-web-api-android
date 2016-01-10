package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for {@link kaaes.spotify.webapi.android.models.Playlist} and
 * {@link kaaes.spotify.webapi.android.models.PlaylistSimple}
 */
public abstract class PlaylistBase implements Parcelable {
    public Boolean collaborative;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public List<Image> images;
    public String name;
    public UserPublic owner;
    @SerializedName("public")
    public Boolean is_public;
    public String snapshot_id;
    public String type;
    public String uri;

    protected PlaylistBase() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (collaborative ? 1 : 0));

        dest.writeInt(external_urls.size());
        for(Map.Entry<String, String> entry : external_urls.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }

        dest.writeString(href);
        dest.writeString(id);
        dest.writeList(images);
        dest.writeString(name);
        dest.writeParcelable(owner, flags);
        dest.writeByte((byte) (is_public ? 1 : 0));
        dest.writeString(snapshot_id);
        dest.writeString(type);
        dest.writeString(uri);
    }

    protected PlaylistBase(Parcel in) {
        this.collaborative = in.readByte() != 0;

        int size = in.readInt();
        external_urls = new HashMap<>();
        for(int i = 0; i < size; i++) {
            String key = in.readString();
            String val = in.readString();
            this.external_urls.put(key, val);
        }

        this.href = in.readString();
        this.id = in.readString();

        images = new ArrayList<>();
        in.readList(images, Image.class.getClassLoader());

        this.name = in.readString();
        this.owner = in.readParcelable(Image.class.getClassLoader());
        this.is_public = in.readByte() != 0;
        this.snapshot_id = in.readString();
        this.type = in.readString();
        this.uri = in.readString();
    }
}
