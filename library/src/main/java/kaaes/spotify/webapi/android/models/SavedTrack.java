package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#saved-track-object">Saved track object model</a>
 */
public class SavedTrack implements Parcelable {
    public String added_at;
    public Track track;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.added_at);
        dest.writeParcelable(this.track, 0);
    }

    public SavedTrack() {
    }

    protected SavedTrack(Parcel in) {
        this.added_at = in.readString();
        this.track = in.readParcelable(Track.class.getClassLoader());
    }

    public static final Parcelable.Creator<SavedTrack> CREATOR = new Parcelable.Creator<SavedTrack>() {
        public SavedTrack createFromParcel(Parcel source) {
            return new SavedTrack(source);
        }

        public SavedTrack[] newArray(int size) {
            return new SavedTrack[size];
        }
    };
}
