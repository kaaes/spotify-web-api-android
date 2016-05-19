package io.github.kaaes.spotify.webapi.samplesearch;

import io.github.kaaes.spotify.webapi.core.models.Track;

import java.util.List;

public class Search {

    public interface View {
        void reset();

        void addData(List<Track> items);
    }

    public interface ActionListener {

        void init(String token);

        String getCurrentQuery();

        void search(String searchQuery);

        void loadMoreResults();

        void selectTrack(Track item);

        void resume();

        void pause();

        void destroy();

    }
}
