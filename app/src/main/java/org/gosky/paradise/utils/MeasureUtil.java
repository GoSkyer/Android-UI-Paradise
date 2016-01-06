package org.gosky.paradise.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;

/**
 *
 * @author galaxy captain
 * @date 2015/12/14
 */
public class MeasureUtil {


    /**
     * 说明：dip转换为px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 说明：px转换为dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 说明：sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 主动测量View高度
     */
    public static int measureHeight(View view) {
        int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measure, measure);
        return view.getMeasuredHeight();
    }

    /**
     * 主动测量View宽度
     */
    public static int measureWidth(View view) {
        int measure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measure, measure);
        return view.getMeasuredWidth();
    }

    /**
     * 获取屏幕高度和宽度
     *
     * @return Point
     */
    public static Point getWindowSize(Context context) {
        Point p = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(p);
        return p;
    }

    /**
     * 获得屏幕宽度
     */
    public static int getWindowWidth(Context context) {
        Point p = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(p);
        return p.x;
    }

    /**
     * 获得屏幕高度
     */
    public static int getWindowHeight(Context context) {
        Point p = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(p);
        return p.y;
    }


}
