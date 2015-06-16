package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SnapshotId implements Parcelable {
    public String snapshot_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.snapshot_id);
    }

    public SnapshotId() {
    }

    protected SnapshotId(Parcel in) {
        this.snapshot_id = in.readString();
    }

    public static final Parcelable.Creator<SnapshotId> CREATOR = new Parcelable.Creator<SnapshotId>() {
        public SnapshotId createFromParcel(Parcel source) {
            return new SnapshotId(source);
        }

        public SnapshotId[] newArray(int size) {
            return new SnapshotId[size];
        }
    };
}
