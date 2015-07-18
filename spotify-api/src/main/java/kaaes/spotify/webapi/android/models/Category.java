package kaaes.spotify.webapi.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Category implements Parcelable {
  public String href;
  public List<Image> icons;
  public String id;
  public String name;

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.href);
    dest.writeTypedList(icons);
    dest.writeString(this.id);
    dest.writeString(this.name);
  }

  public Category() {
  }

  protected Category(Parcel in) {
    this.href = in.readString();
    this.icons = in.createTypedArrayList(Image.CREATOR);
    this.id = in.readString();
    this.name = in.readString();
  }

  public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
    public Category createFromParcel(Parcel source) {
      return new Category(source);
    }

    public Category[] newArray(int size) {
      return new Category[size];
    }
  };
}
