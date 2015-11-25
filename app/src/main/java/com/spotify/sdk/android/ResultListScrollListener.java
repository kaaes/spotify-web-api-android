/*
 * Copyright (c) 2015 Spotify AB
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package com.spotify.sdk.android;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class ResultListScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = ResultListScrollListener.class.getSimpleName();

    private final LinearLayoutManager mLayoutManager;

    private int mItemCount;

    private boolean mAwaitingItems = true;

    public ResultListScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public void reset() {
        mItemCount = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int itemCount = mLayoutManager.getItemCount();
        int itemPosition = mLayoutManager.findLastVisibleItemPosition();

        if (mAwaitingItems && itemCount > mItemCount) {
            mItemCount = itemCount;
            mAwaitingItems = false;
        }

        Log.d(TAG, String.format("loading %s, item count: %s/%s, itemPosition %s", mAwaitingItems, mItemCount, itemCount, itemPosition));

        if (!mAwaitingItems && (itemCount <= itemPosition + 1)) {
            mAwaitingItems = true;
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
