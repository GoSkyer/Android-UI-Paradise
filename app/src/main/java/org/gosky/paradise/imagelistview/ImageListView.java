package org.gosky.paradise.imagelistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import org.gosky.paradise.utils.MeasureUtil;

import java.util.List;

/**
 * 多图片展示，添加，修改
 *
 * @author galaxy captain
 * @date 2016/1/4
 */
public class ImageListView extends RecyclerView {

    private Context mContext;

    private ImageView[] imgs;

    private ImageListViewLayoutManager layoutManager;
    private ImageListViewAdapter adapter;

    private int mWindowWidth = 0;

    private int mMargin = 0;
    private boolean isAdd = false;
    private int imgID = View.NO_ID;

    public ImageListView(Context context) {
        this(context, null);
    }

    public ImageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(OVER_SCROLL_NEVER);
        init(context);
    }

    public void attr(AttributeSet attrs) {
        TypedArray a;
    }

    private void init(Context context) {
        this.mContext = context;
        //获得屏幕高度
        mWindowWidth = MeasureUtil.getWindowWidth(mContext);
    }

    private void initView(int count) {
        //初始化布局管理
        layoutManager = new ImageListViewLayoutManager(mContext, count);
        setLayoutManager(layoutManager);
        //初始化适配器
        adapter = new ImageListViewAdapter(mContext);
        setAdapter(adapter);
    }

    public void setOnImageAddClickListener(OnImageAddClick onImageAddClick) {
        adapter.setOnImageAddClick(onImageAddClick);
    }

    public void setOnImageItemClickListener(OnImageItemClick onImageItemClick) {
        adapter.setOnImageItemClick(onImageItemClick);
    }

    /**
     * 边距
     *
     * @param margin 单位：px
     */
    public void setMargin(int margin) {
        this.mMargin = margin;
    }

    /**
     * 使用添加功能
     *
     * @param isAdd true使用添加功能，false不使用添加功能
     * @param imgID 显示的添加图片
     */
    public void isAdd(int imgID, boolean isAdd) {
        this.isAdd = isAdd;
        this.imgID = imgID;
    }

    /**
     * 初始化控件显示内容
     *
     * @param maxLength 在窗口内显示每一行图片的最大数量
     * @param isMore    是否显示更多提示，如果true，则单排显示
     */
    public void init(int maxLength, boolean isMore) {
        int margin = mMargin == 0 ? MeasureUtil.dip2px(mContext, 2) : mMargin;
        int width = (mWindowWidth - margin * 6) / maxLength;
        initView(maxLength);
        adapter.setMore(isMore);
        adapter.isAdd(isAdd, imgID);
        adapter.setSize(width, margin, maxLength);
    }

    /**
     * 添加图片
     *
     * @param path
     */
    public void addItem(String path) {
        adapter.addItem(-1, path);
    }

    /**
     * 添加图片
     *
     * @param position
     * @param path
     */
    public void addItem(int position, String path) {
        adapter.addItem(position, path);
    }

    /**
     * 添加图片
     *
     * @param list
     */
    public void addAllItem(List<String> list) {
        adapter.addAllItem(list);
    }

    /**
     * 根据位置获得图片路径
     *
     * @param position
     */
    public String getPath(int position) {
        return adapter.getAllPath().get(position);
    }

    /**
     * 获得全部图片路径
     */
    public List<String> getAllPath() {
        return adapter.getAllPath();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

}
