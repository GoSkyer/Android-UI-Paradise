package org.gosky.paradise.refreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 支持上拉加载更多的布局
 *
 * @author galaxy captain
 * @date 2016/1/3
 */
public abstract class AbsPullupLayout extends AbsRefreshLayout {

    protected boolean isLoading = false;

    public AbsPullupLayout(Context context) {
        super(context);
    }

    public AbsPullupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsPullupLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onPullupStart(int moveY) {
        if (!isLoading) {
            moveY = (int) (moveY * 0.3);

            if (Math.abs(moveY) > mFooterHeight) {
                moveBodyInY(-mFooterHeight);
                moveFooterInY(0);
            } else {
                moveBodyInY(moveY);
                moveFooterInY(mFooterHeight + moveY);
            }
        }
    }

    @Override
    public void onPullupEnd() {
        if (!isLoading)
            toLoad();
    }

    /**
     * 设置到加载更多打开的位置
     */
    private void toLoad() {

        // 设置回到加载更多打开的位置
        moveBodyInY(-mFooterHeight);
        moveFooterInY(0);

        if (onLoadListener != null) {
            isLoading = true;
            onLoadListener.onLoadMore();
        }

    }

    /**
     * @param isLoading true打开加载更多(暂未实现)，<br>
     *                  false关闭加载更多
     */
    public void setLoading(boolean isLoading) {
        if (isLoading) {
            //TODO
        } else {
            this.isLoading = false;
            closeLoading();
        }
    }

    private void closeLoading() {
        // 设置回到正常的布局位置
        moveBodyInY(0);
        moveFooterInY(0);
    }

    public void setFootView(RefreshView view) {

        footRefreshView = view;

        if (footRefreshView == null) return;

        mFooter = footRefreshView.createView();

        if (mFooter != null) {
            mFooterHeight = getViewHeight(mFooter);

            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mFooterHeight);
            params.addRule(ALIGN_PARENT_BOTTOM);
            addView(mFooter, 0, params);
        }

    }

    private OnLoadListener onLoadListener;

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }
}
