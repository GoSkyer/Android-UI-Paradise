package org.gosky.paradise.refreshlayout.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.gosky.paradise.refreshlayout.AbsPullupLayout;
import org.gosky.paradise.refreshlayout.OnRefreshListener;
import org.gosky.paradise.refreshlayout.RefreshView;
import org.gosky.paradise.utils.MeasureUtil;


/**
 * 拖拽出悬浮物，下拉刷新
 *
 * @author galaxy captain
 * @date 2016/1/3
 */
public class TwoRefreshLayout extends AbsPullupLayout {

    /**
     * 位移量阻塞度
     */
    protected static double OFFSET_SCALE = 0.3;

    /**
     * 最大位移量
     */
    protected static int OFFSET_HEADER_MAX = 200;

    public TwoRefreshLayout(Context context) {
        this(context, null);
    }

    public TwoRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        OFFSET_HEADER_MAX = MeasureUtil.dip2px(context, 60);
    }

    public void setHeadView(RefreshView view) {

        headRefreshView = view;

        if (headRefreshView == null) return;

        mHeader = headRefreshView.createView();

        if (mHeader != null) {
            mHeaderHeight = getViewHeight(mHeader);

            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeaderHeight);
            params.addRule(ALIGN_PARENT_TOP);
            addView(mHeader, params);
            mHeader.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPulldownStart(int moveY) {
        if (!isLoading) {
            if (mHeader.getVisibility() == View.GONE) mHeader.setVisibility(View.VISIBLE);
            moveHeaderInY((int) (moveY * OFFSET_SCALE));

            if (moveY > OFFSET_HEADER_MAX / 2) {
                headRefreshView.onExecState(moveY);
            } else {
                headRefreshView.onNomarlState(moveY);
            }

        }
    }

    @Override
    public void onPulldownEnd() {
        toRefresh();
    }

    @Override
    public void setRefreshing(boolean isRefresh) {
        if (isRefresh) {
            //TODO
        } else {
            closeRefresh();
        }
    }

    private void toRefresh() {
        if (onRefreshListener != null) {
            isLoading = true;
            onRefreshListener.onRefresh();
            headRefreshView.onDoState();
        }

        moveHeaderInY(OFFSET_HEADER_MAX);
    }

    private void closeRefresh() {
        isLoading = false;
        mHeader.setVisibility(View.GONE);
    }

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

}
