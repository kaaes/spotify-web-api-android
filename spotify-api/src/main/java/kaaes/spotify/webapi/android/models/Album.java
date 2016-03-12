package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#album-object-full">Album object model</a>
 */
public class Album extends AlbumSimple implements Parcelable {
    public List<ArtistSimple> artists;
    public List<Copyright> copyrights;
    public Map<String, String> external_ids;
    public List<String> genres;
    public Integer popularity;
    public String release_date;
    public String release_date_precision;
    public Pager<TrackSimple> tracks;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(artists);
        dest.writeTypedList(copyrights);
        dest.writeMap(this.external_ids);
        dest.writeStringList(this.genres);
        dest.writeValue(this.popularity);
        dest.writeString(this.release_date);
        dest.writeString(this.release_date_precision);
        dest.writeParcelable(this.tracks, flags);
    }

    public Album() {
    }

    protected Album(Parcel in) {
        super(in);
        this.artists = in.createTypedArrayList(ArtistSimple.CREATOR);
        this.copyrights = in.createTypedArrayList(Copyright.CREATOR);
        this.external_ids = in.readHashMap(ClassLoader.getSystemClassLoader());
        this.genres = in.createStringArrayList();
        this.popularity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.release_date = in.readString();
        this.release_date_precision = in.readString();
        this.tracks = in.readParcelable(Pager.class.getClassLoader());
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}