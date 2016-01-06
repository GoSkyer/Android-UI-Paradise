package org.gosky.paradise.refreshlayout.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import org.gosky.paradise.refreshlayout.AbsPullupLayout;
import org.gosky.paradise.refreshlayout.OnRefreshListener;
import org.gosky.paradise.refreshlayout.RefreshView;
import org.gosky.paradise.utils.MeasureUtil;


/**
 * 支持下拉刷新的布局
 *
 * @author galaxy captain
 * @date 2015/12/29
 */
public class OneRefreshLayout extends AbsPullupLayout {

    /**
     * 位移量阻塞度
     */
    protected static double OFFSET_SCALE = 0.3;

    /**
     * 最大位移量
     */
    protected static int OFFSET_HEADER_HEIGHT = 0;

    /**
     * 动画播放的时间
     */
    private static final int ANIM_TIME = 450;

    public OneRefreshLayout(Context context) {
        this(context, null);
    }

    public OneRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OneRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        OFFSET_HEADER_HEIGHT = MeasureUtil.dip2px(context, 60);
    }

    @Override
    public void onPulldownStart(int moveY) {
        if (!isLoading) {
            moveY = (int) (moveY * OFFSET_SCALE);
            moveBodyInY(moveY);
            moveHeaderInY(-mHeaderHeight + moveY);

            if (moveY < mHeaderHeight / 2) {
                headRefreshView.onNomarlState(moveY);
            } else if (moveY > mHeaderHeight / 2) {
                headRefreshView.onExecState(moveY);
            }

        }
    }

    @Override
    public void onPulldownEnd() {
        if (!isLoading) {
            if (moveY > mHeaderHeight) {
                toRefresh();
            } else {
                setRefreshing(false);
            }
        }
    }

    @Override
    public void setRefreshing(boolean isLoading) {
        if (isLoading) {
            //打开刷新
            openRefresh();
        } else {
            //关闭刷新
            closeRefresh();
        }
    }

    /**
     * 打开下拉刷新
     */
    private void openRefresh() {
        TranslateAnimation anim = new TranslateAnimation(
                0, //fromX
                0, //toX
                0,//fromY
                mHeaderHeight);//toY

        anim.setDuration(ANIM_TIME);//动画进行时间

        getBodyView().startAnimation(anim);

        // 设置回到刷新打开的位置
        moveBodyInY(mHeaderHeight);
    }

    /**
     * 下拉刷新时，关闭下拉刷新
     */
    private void closeRefresh() {

        /**
         * fromX:0,
         * toX:0,
         * fromY:主视图当前的Y坐标,
         * toY:记录中的原始位置的Y坐标
         */
        TranslateAnimation anim = new TranslateAnimation(
                0, //fromX
                0, //toX
                getBodyView().getTop(),//fromY
                getBodyRect().top);//toY

        anim.setDuration(ANIM_TIME);//动画进行时间

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isLoading = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        getBodyView().startAnimation(anim);

        // 设置回到正常的布局位置
        moveBodyInY(0);

    }

    /**
     * 下拉状态时，弹回下拉刷新位置
     */
    private void toRefresh() {

        if (onRefreshListener != null) {
            isLoading = true;
            onRefreshListener.onRefresh();
            headRefreshView.onDoState();
        }

        // 设置回到刷新打开的位置
        moveBodyInY(mHeaderHeight);
        moveHeaderInY(0);
    }


    /**
     * 设置下拉刷新头
     *
     * @param view
     */
    public void setHeadView(RefreshView view) {

        headRefreshView = view;

        if (headRefreshView == null) return;

        mHeader = headRefreshView.createView();

        if (mHeader != null) {
//            mHeaderHeight = getViewHeight(mHeader);
            mHeaderHeight = OFFSET_HEADER_HEIGHT;

            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeaderHeight);
            addView(mHeader, 0, params);

        }

    }

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}
