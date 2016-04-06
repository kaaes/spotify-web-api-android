package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#audio-features-object">Audio Features object model</a>
 * @author Andreas Nyhrén
 */
public class AudioFeature implements Parcelable {
    public double acousticness;
    public String analysis_url;
    public double danceability;
    public long duration_ms;
    public double energy;
    public String id;
    public double instrumentalness;
    public int key;
    public double liveness;
    public double loudness;
    public int mode;
    public double speechiness;
    public double tempo;
    public int time_signature;
    public String track_href;
    public String type;
    public String uri;
    public double valance;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.acousticness);
        dest.writeString(this.analysis_url);
        dest.writeDouble(this.danceability);
        dest.writeLong(this.duration_ms);
        dest.writeDouble(this.energy);
        dest.writeString(this.id);
        dest.writeDouble(this.instrumentalness);
        dest.writeInt(this.key);
        dest.writeDouble(this.liveness);
        dest.writeDouble(this.loudness);
        dest.writeInt(this.mode);
        dest.writeDouble(this.speechiness);
        dest.writeDouble(this.tempo);
        dest.writeInt(this.time_signature);
        dest.writeString(this.track_href);
        dest.writeString(this.type);
        dest.writeString(this.uri);
        dest.writeDouble(this.valance);
    }


    public AudioFeature() {
    }

    protected AudioFeature(Parcel in) {
        this.acousticness = in.readDouble();
        this.analysis_url = in.readString();
        this.danceability = in.readDouble();
        this.duration_ms = in.readLong();
        this.energy = in.readDouble();
        this.id = in.readString();
        this.instrumentalness = in.readDouble();
        this.key = in.readInt();
        this.liveness = in.readDouble();
        this.loudness = in.readDouble();
        this.mode = in.readInt();
        this.speechiness = in.readDouble();
        this.tempo = in.readDouble();
        this.time_signature = in.readInt();
        this.track_href = in.readString();
        this.type = in.readString();
        this.uri = in.readString();
        this.valance = in.readDouble();

    }

    public static final Creator<AudioFeature> CREATOR = new Creator<AudioFeature>() {
        public AudioFeature createFromParcel(Parcel source) {
            return new AudioFeature(source);
        }

        public AudioFeature[] newArray(int size) {
            return new AudioFeature[size];
        }
    };
}
