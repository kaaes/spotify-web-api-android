package com.spotify.sdk.android;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Joiner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private static final String TAG = SearchResultsAdapter.class.getSimpleName();

    private final List<Track> mItems = new ArrayList<>();
    private final Activity mContext;
    private final SpotifyService mSpotifyApi;
    private final ItemSelectedListener mListener;
    private int mOffset;
    private String mQuery;
    private int mLimit;

    private boolean mHasMoreItems = true;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView title;
        public final TextView subtitle;
        public final ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.entity_title);
            subtitle = (TextView) itemView.findViewById(R.id.entity_subtitle);
            image = (ImageView) itemView.findViewById(R.id.entity_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(getLayoutPosition());
            mListener.onItemSelected(v, mItems.get(getAdapterPosition()));
        }
    }

    public interface ItemSelectedListener {
        void onItemSelected(View itemView, Track item);
    }

    public SearchResultsAdapter(Activity context, SpotifyService spotifyService, ItemSelectedListener listener) {
        mContext = context;
        mSpotifyApi = spotifyService;
        mListener = listener;
    }

    private void clearData() {
        mItems.clear();
    }

    private void addData(List<Track> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void getNextPage() {
        mOffset += mLimit;
        getData();
    }

    public void searchTracks(String query, int pageSize) {
        mQuery = query;
        mLimit = pageSize;
        mOffset = 0;
        clearData();
        getData();
    }

    private void getData( ) {
        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.OFFSET, mOffset);
        options.put(SpotifyService.LIMIT, mLimit);
        mSpotifyApi.searchTracks(mQuery, options, new Callback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {
                mHasMoreItems = tracksPager.tracks.total > tracksPager.tracks.offset + tracksPager.tracks.limit;
                addData(tracksPager.tracks.items);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track item = mItems.get(position);

        holder.title.setText(item.name);

        List<String> names = new ArrayList<>();
        for (ArtistSimple i : item.artists) {
            names.add(i.name);
        }
        Joiner joiner = Joiner.on(", ");
        holder.subtitle.setText(joiner.join(names));

        Image image = item.album.images.get(0);
        if (image != null) {
            Picasso.with(mContext).load(image.url).into(holder.image);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
