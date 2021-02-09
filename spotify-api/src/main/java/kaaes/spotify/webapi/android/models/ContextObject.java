package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class ContextObject implements Parcelable {
    public Map<String, String> external_urls;
    public String href;
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
        dest.writeString(this.type);
        dest.writeString(this.uri);
    }

    public ContextObject(){

    }

    protected ContextObject(Parcel in){
        this.external_urls = in.readHashMap(Map.class.getClassLoader());
        this.href = in.readString();
        this.type = in.readString();
        this.uri = in.readString();
    }

    public static final Creator<ContextObject> CREATOR = new Creator<ContextObject>() {
        public ContextObject createFromParcel(Parcel source) {
            return new ContextObject(source);
        }

        public ContextObject[] newArray(int size) {
            return new ContextObject[size];
        }
    };
}
