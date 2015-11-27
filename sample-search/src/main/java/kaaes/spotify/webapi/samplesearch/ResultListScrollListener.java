package kaaes.spotify.webapi.samplesearch;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class ResultListScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = ResultListScrollListener.class.getSimpleName();

    private final LinearLayoutManager mLayoutManager;

    private static final int SCROLL_BUFFER = 3;
    private int mCurrentItemCount = 0;

    private boolean mAwaitingItems = true;

    public ResultListScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public void reset() {
        mCurrentItemCount = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int itemCount = mLayoutManager.getItemCount();
        int itemPosition = mLayoutManager.findLastVisibleItemPosition();

        if (mAwaitingItems && itemCount > mCurrentItemCount) {
            mCurrentItemCount = itemCount;
            mAwaitingItems = false;
        }

        Log.d(TAG, String.format("loading %s, item count: %s/%s, itemPosition %s", mAwaitingItems, mCurrentItemCount, itemCount, itemPosition));

        if (!mAwaitingItems && itemPosition + 1 >= itemCount - SCROLL_BUFFER) {
            mAwaitingItems = true;
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
