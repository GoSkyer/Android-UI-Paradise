package org.gosky.paradise.refreshlayout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * 下拉、上拉布局
 *
 * @author galaxy captain
 * @date 2015/12/25
 */
public abstract class AbsRefreshLayout extends RelativeLayout {

    private static final String TAG = "TAG";

    private Context mContext;

    protected IRefreshState mRefreshState;

    private LinearLayout headContainer, footContainer;
    protected RefreshView headRefreshView, footRefreshView;
    protected View mHeader, mFooter, mBody;


    /**
     * 是否可以下拉刷新
     */
    private boolean isCanPullDown = false;

    /**
     * 是否可以上拉加载
     */
    private boolean isCanPullUp = false;

    /**
     * 下拉刷新布局的高度，上拉加载布局的高度
     */
    protected int mHeaderHeight, mFooterHeight;

    protected Rect bodyRect = new Rect();
    protected Rect headRect = new Rect();
    protected Rect footRect = new Rect();

    public AbsRefreshLayout(Context context) {
        this(context, null);
    }

    public AbsRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    protected void init() {
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount(); i++) {

            mBody = getChildAt(i);

            if (mBody instanceof ListView) {

                mRefreshState = new ListViewRefreshState((ListView) mBody);

            }

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (mBody != null && bodyRect.isEmpty()) {
            bodyRect.set(mBody.getLeft(), mBody.getTop(), mBody.getRight(), mBody.getBottom());
            Log.e("TAG", bodyRect.toShortString());
        }

        if (mHeader != null && headRect.isEmpty()) {
            headRect.set(mHeader.getLeft(), mHeader.getTop(), mHeader.getRight(), mHeader.getBottom());
            Log.e("TAG", headRect.toShortString());
        }

        if (mFooter != null && footRect.isEmpty()) {
            footRect.set(mFooter.getLeft(), mFooter.getTop(), mFooter.getRight(), mFooter.getBottom());
            Log.e("TAG", footRect.toShortString());
        }

    }

    /**
     * 测量View的高度
     *
     * @param view
     * @return
     */
    protected int getViewHeight(View view) {
        if (view.getHeight() != 0) return view.getHeight();
        int measureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(measureSpec, measureSpec);
        return view.getMeasuredHeight();
    }

    protected Rect getBodyRect() {
        return bodyRect;
    }

    protected View getBodyView() {
        return mBody;
    }

    public View getHeader() {
        return mHeader;
    }

    public View getFooter() {
        return mFooter;
    }

    private float startY = 0;

    private boolean hasMoved = false;

    protected int moveY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (mRefreshState == null) {
            return super.dispatchTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                isCanPullDown = mRefreshState.canPullDown();

                if (!isCanPullDown)
                    isCanPullUp = mRefreshState.canPullUp();
                else
                    isCanPullUp = false;

                startY = ev.getY();

//                Log.e(TAG, (isCanPullDown ? "可以下拉刷新" : "不可以下拉刷新") + "," + (isCanPullUp ? "可以上拉加载" : "不可以上拉加载"));

            case MotionEvent.ACTION_UP:

                if (!hasMoved) break;

                if (isCanPullDown) {
                    onPulldownEnd();
                } else if (isCanPullUp) {
                    onPullupEnd();
                }

                isCanPullDown = false;
                isCanPullUp = false;
                hasMoved = false;

                break;
            case MotionEvent.ACTION_MOVE:

                //在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
                if (!isCanPullDown && !isCanPullUp) {
                    startY = ev.getY();
                    isCanPullDown = mRefreshState.canPullDown();
                    isCanPullUp = mRefreshState.canPullUp();
                    break;
                }

                float flagY = ev.getY();
                moveY = (int) (flagY - startY);

                boolean canMove = (isCanPullDown && moveY > 0) || (isCanPullUp && moveY < 0);

                if (canMove) {

                    if (moveY > 0) {
                        onPulldownStart(moveY);
                    } else if (moveY < 0) {
                        onPullupStart(moveY);
                    }

                    hasMoved = true;

                }

                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (hasMoved) return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (hasMoved) return true;
        return super.onTouchEvent(event);
    }

    /**
     * 在Y坐标轴上移动主内容
     *
     * @param moveY
     */
    public void moveBodyInY(int moveY) {
        mBody.layout(bodyRect.left, bodyRect.top + moveY, bodyRect.right, bodyRect.bottom + moveY);
    }

    /**
     * 在Y坐标轴上移动头内容
     *
     * @param moveY
     */
    public void moveHeaderInY(int moveY) {
        mHeader.layout(headRect.left, headRect.top + moveY, headRect.right, headRect.bottom + moveY);
    }

    /**
     * 在Y坐标轴上移动脚内容
     *
     * @param moveY
     */
    public void moveFooterInY(int moveY) {
        mFooter.layout(footRect.left, footRect.top + moveY, footRect.right, footRect.bottom + moveY);
    }

    public abstract void onPulldownStart(int moveY);

    public abstract void onPulldownEnd();

    public abstract void onPullupStart(int moveY);

    public abstract void onPullupEnd();

    public abstract void setRefreshing(boolean isRefresh);

}
