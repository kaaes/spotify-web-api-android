package kaaes.spotify.webapi.android.models;

import android.os.Parcel;

import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#artist-object-full">Artist object model</a>
 */
public class Artist extends ArtistSimple {
    public Followers followers;
    public List<String> genres;
    public List<Image> images;
    public Integer popularity;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.followers, flags);
        dest.writeStringList(this.genres);
        dest.writeTypedList(images);
        dest.writeValue(this.popularity);
    }

    public Artist() {
    }

    protected Artist(Parcel in) {
        super(in);
        this.followers = in.readParcelable(Followers.class.getClassLoader());
        this.genres = in.createStringArrayList();
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.popularity = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}