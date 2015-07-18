package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

public class TrackSimple implements Parcelable {
    public List<ArtistSimple> artists;
    public List<String> available_markets;
    public Boolean is_playable;
    public LinkedTrack linked_from;
    public int disc_number;
    public long duration_ms;
    public Boolean explicit;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public String name;
    public String preview_url;
    public int track_number;
    public String type;
    public String uri;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(artists);
        dest.writeStringList(this.available_markets);
        dest.writeValue(this.is_playable);
        dest.writeParcelable(this.linked_from, 0);
        dest.writeInt(this.disc_number);
        dest.writeLong(this.duration_ms);
        dest.writeValue(this.explicit);
        dest.writeMap(this.external_urls);
        dest.writeString(this.href);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.preview_url);
        dest.writeInt(this.track_number);
        dest.writeString(this.type);
        dest.writeString(this.uri);
    }

    public TrackSimple() {
    }

    protected TrackSimple(Parcel in) {
        this.artists = in.createTypedArrayList(ArtistSimple.CREATOR);
        this.available_markets = in.createStringArrayList();
        this.is_playable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.linked_from = in.readParcelable(LinkedTrack.class.getClassLoader());
        this.disc_number = in.readInt();
        this.duration_ms = in.readLong();
        this.explicit = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.external_urls = in.readHashMap(Map.class.getClassLoader());
        this.href = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.preview_url = in.readString();
        this.track_number = in.readInt();
        this.type = in.readString();
        this.uri = in.readString();
    }

    public static final Creator<TrackSimple> CREATOR = new Creator<TrackSimple>() {
        public TrackSimple createFromParcel(Parcel source) {
            return new TrackSimple(source);
        }

        public TrackSimple[] newArray(int size) {
            return new TrackSimple[size];
        }
    };
}
