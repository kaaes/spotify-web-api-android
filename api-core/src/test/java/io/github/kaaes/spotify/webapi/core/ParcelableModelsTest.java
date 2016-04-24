package io.github.kaaes.spotify.webapi.core;

import android.os.Parcel;
import android.os.Parcelable;

import org.fest.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.github.kaaes.spotify.webapi.core.models.Album;
import io.github.kaaes.spotify.webapi.core.models.AlbumSimple;
import io.github.kaaes.spotify.webapi.core.models.Albums;
import io.github.kaaes.spotify.webapi.core.models.AlbumsPager;
import io.github.kaaes.spotify.webapi.core.models.Artist;
import io.github.kaaes.spotify.webapi.core.models.ArtistSimple;
import io.github.kaaes.spotify.webapi.core.models.Artists;
import io.github.kaaes.spotify.webapi.core.models.ArtistsPager;
import io.github.kaaes.spotify.webapi.core.models.AudioFeaturesTrack;
import io.github.kaaes.spotify.webapi.core.models.AudioFeaturesTracks;
import io.github.kaaes.spotify.webapi.core.models.CategoriesPager;
import io.github.kaaes.spotify.webapi.core.models.Category;
import io.github.kaaes.spotify.webapi.core.models.Copyright;
import io.github.kaaes.spotify.webapi.core.models.ErrorDetails;
import io.github.kaaes.spotify.webapi.core.models.ErrorResponse;
import io.github.kaaes.spotify.webapi.core.models.FeaturedPlaylists;
import io.github.kaaes.spotify.webapi.core.models.Followers;
import io.github.kaaes.spotify.webapi.core.models.Image;
import io.github.kaaes.spotify.webapi.core.models.LinkedTrack;
import io.github.kaaes.spotify.webapi.core.models.NewReleases;
import io.github.kaaes.spotify.webapi.core.models.Playlist;
import io.github.kaaes.spotify.webapi.core.models.PlaylistFollowPrivacy;
import io.github.kaaes.spotify.webapi.core.models.PlaylistSimple;
import io.github.kaaes.spotify.webapi.core.models.PlaylistTrack;
import io.github.kaaes.spotify.webapi.core.models.PlaylistTracksInformation;
import io.github.kaaes.spotify.webapi.core.models.PlaylistsPager;
import io.github.kaaes.spotify.webapi.core.models.Recommendations;
import io.github.kaaes.spotify.webapi.core.models.Result;
import io.github.kaaes.spotify.webapi.core.models.SavedTrack;
import io.github.kaaes.spotify.webapi.core.models.Seed;
import io.github.kaaes.spotify.webapi.core.models.SeedsGenres;
import io.github.kaaes.spotify.webapi.core.models.SnapshotId;
import io.github.kaaes.spotify.webapi.core.models.Track;
import io.github.kaaes.spotify.webapi.core.models.TrackSimple;
import io.github.kaaes.spotify.webapi.core.models.TrackToRemove;
import io.github.kaaes.spotify.webapi.core.models.TrackToRemoveWithPosition;
import io.github.kaaes.spotify.webapi.core.models.Tracks;
import io.github.kaaes.spotify.webapi.core.models.TracksPager;
import io.github.kaaes.spotify.webapi.core.models.TracksToRemove;
import io.github.kaaes.spotify.webapi.core.models.TracksToRemoveWithPosition;
import io.github.kaaes.spotify.webapi.core.models.UserPrivate;
import io.github.kaaes.spotify.webapi.core.models.UserPublic;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ParcelableModelsTest {

    private static final String TEST_DATA_DIR = "/fixtures/";
    private static final int MAX_TEST_DATA_FILE_SIZE = 131072;

    public static String readTestData(String fileName) {
        try {
            String path = Robolectric.class.getResource(TEST_DATA_DIR + fileName).toURI().getPath();
            return readFromFile(new File(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String readFromFile(File file) throws IOException {
        Reader reader = new FileReader(file);
        CharBuffer charBuffer = CharBuffer.allocate(MAX_TEST_DATA_FILE_SIZE);
        reader.read(charBuffer);
        charBuffer.position(0);
        return charBuffer.toString().trim();
    }

    @Test
    public void allParcelables() throws IllegalAccessException, InstantiationException, NoSuchFieldException {

        ModelPopulator populator = new ModelPopulator("CREATOR", "$jacocoData");

        for (Class<? extends Parcelable> modelClass : getModelClasses()) {

            Parcelable instance = populator.populateWithRandomValues(modelClass);

            testSingleParcelable(instance);
            testParcelableArray(instance);

            /* Trick to increase code coverage */
            instance.describeContents();
            ((Parcelable.Creator<?>) modelClass.getField("CREATOR").get(null)).newArray(13);
        }
    }

   List<Class<? extends Parcelable>> getModelClasses() {
        return java.util.Arrays.asList(
                Album.class,
                Albums.class,
                AlbumSimple.class,
                AlbumsPager.class,
                Artist.class,
                Artists.class,
                ArtistSimple.class,
                ArtistsPager.class,
                AudioFeaturesTrack.class,
                AudioFeaturesTracks.class,
                CategoriesPager.class,
                Category.class,
                Copyright.class,
                ErrorDetails.class,
                ErrorResponse.class,
                FeaturedPlaylists.class,
                Followers.class,
                Image.class,
                LinkedTrack.class,
                NewReleases.class,
                Playlist.class,
                PlaylistFollowPrivacy.class,
                PlaylistSimple.class,
                PlaylistsPager.class,
                PlaylistTrack.class,
                PlaylistTracksInformation.class,
                Recommendations.class,
                Result.class,
                Seed.class,
                SeedsGenres.class,
                SavedTrack.class,
                SnapshotId.class,
                Track.class,
                Tracks.class,
                TrackSimple.class,
                TracksPager.class,
                TrackToRemove.class,
                TrackToRemoveWithPosition.class,
                TracksToRemove.class,
                TracksToRemoveWithPosition.class,
                UserPrivate.class,
                UserPublic.class
        );
    }

    <T extends Parcelable> void testSingleParcelable(T underTest) {

        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(underTest, 0);
        parcel.setDataPosition(0);
        T fromParcel = parcel.readParcelable(underTest.getClass().getClassLoader());

        ModelAssert.assertThat(fromParcel).isEqualByComparingFields(underTest);
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