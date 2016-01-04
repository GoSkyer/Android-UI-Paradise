package org.gosky.paradise.refreshlayout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author galaxy captain
 * @date 2015/12/28
 */
public class RecyclerViewRefreshState implements IRefreshState {

    private RecyclerView mRecyclerView;

    public RecyclerViewRefreshState(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Override
    public boolean canPullDown() {

        LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();

        if (lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop() == 0 && lm.findFirstVisibleItemPosition() == 0)
            return true;

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return false;
    }

    @Override
    public boolean canPullUp() {
        return false;
    }



}
