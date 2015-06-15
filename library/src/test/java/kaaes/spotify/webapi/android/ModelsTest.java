package kaaes.spotify.webapi.android;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.collect.Lists;

import org.fest.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ModelsTest {


    @Test
    public void something() throws IllegalAccessException, InstantiationException {
        for (Class<? extends Parcelable> modelClass : getModelClasses()) {
            Parcelable instance = modelClass.newInstance();

            // TODO(dima) instantiate with random fields
            testSingleParcelable(instance);
            testParcelableArray(instance);
        }
    }

    ArrayList<Class<? extends Parcelable>> getModelClasses() {
        // TODO(dima) read all model classes
        return Lists.newArrayList(
                Album.class,
                Artist.class,
                Track.class
        );
    }

    <T extends Parcelable> void testSingleParcelable(T underTest) {

        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(underTest, 0);
        parcel.setDataPosition(0);
        T fromParcel = parcel.readParcelable(underTest.getClass().getClassLoader());

        assertThat(fromParcel).isEqualsToByComparingFields(underTest);
    }

    <T extends Parcelable> void testParcelableArray(T underTest) {

        Parcel parcel = Parcel.obtain();
        parcel.writeParcelableArray(Arrays.array(underTest, underTest), 0);
        parcel.setDataPosition(0);
        Parcelable[] fromParcel = parcel.readParcelableArray(underTest.getClass().getClassLoader());

        assertThat(fromParcel).hasSize(2);
        assertThat(fromParcel[0]).isEqualsToByComparingFields(underTest);
        assertThat(fromParcel[1]).isEqualsToByComparingFields(underTest);
    }
}