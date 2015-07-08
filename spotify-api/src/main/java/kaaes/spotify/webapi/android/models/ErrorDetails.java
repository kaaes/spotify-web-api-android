package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#error-object">Error object model</a>
 */
public class ErrorDetails implements Parcelable {
    public int status;
    public String message;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.message);
    }

    public ErrorDetails() {
    }

    protected ErrorDetails(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<ErrorDetails> CREATOR = new Parcelable.Creator<ErrorDetails>() {
        public ErrorDetails createFromParcel(Parcel source) {
            return new ErrorDetails(source);
        }

        public ErrorDetails[] newArray(int size) {
            return new ErrorDetails[size];
        }
    };
}
