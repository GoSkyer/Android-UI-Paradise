package org.gosky.paradise.refreshlayout;

import android.view.View;
import android.widget.ListView;

/**
 * @author galaxy captain
 * @date 2015/12/28
 */
public class ListViewRefreshState implements IRefreshState {

    private ListView mListView;

    public ListViewRefreshState(ListView listView) {
        this.mListView = listView;
        this.mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    /**
     * 判断是否可以下拉
     *
     * @return
     */
    @Override
    public boolean canPullDown() {
        if (mListView.getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (mListView.getFirstVisiblePosition() == 0
                && mListView.getChildAt(0).getTop() >= 0) {
            // 滑到顶部了
            return true;
        } else
            return false;
    }

    /**
     * 判断是否可以上拉
     *
     * @return
     */
    @Override
    public boolean canPullUp() {
        if (mListView.getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (mListView.getLastVisiblePosition() == (mListView.getCount() - 1)) {
            // 滑到底部了
            if (mListView.getChildAt(mListView.getLastVisiblePosition() - mListView.getFirstVisiblePosition()) != null
                    && mListView.getChildAt(
                    mListView.getLastVisiblePosition()
                            - mListView.getFirstVisiblePosition()).getBottom() <= mListView.getMeasuredHeight())
                return true;
        }
        return false;
    }

}
