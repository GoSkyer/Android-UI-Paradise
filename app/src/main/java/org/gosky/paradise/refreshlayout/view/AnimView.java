package org.gosky.paradise.refreshlayout.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import org.gosky.paradise.R;
import org.gosky.paradise.refreshlayout.RefreshView;
import org.gosky.paradise.utils.PixelFormat;


/**
 * @author galaxy captain
 * @date 2016/1/3
 */
public class AnimView extends RefreshView {

    private Context context;

    private ImageView img;

    private static float LOGO_MAX_WIDTH = 0;

    public AnimView(Context context) {
        this.context = context;
        LOGO_MAX_WIDTH = (float) PixelFormat.dip2px(context, 64);
    }

    @Override
    public View createView() {

        View view = LayoutInflater.from(context).inflate(R.layout.refresh_anim_head, null);
        img = (ImageView) view.findViewById(R.id.refresh_anim_head_img);
        return view;
    }

    @Override
    public void onNomarlState(int offset) {
        float flag = ((float) offset / LOGO_MAX_WIDTH);
        img.setAlpha(flag);
        if (flag <= 1) {
            img.setScaleX(flag);
            img.setScaleY(flag);
        }
    }

    @Override
    public void onExecState(int offset) {
        float flag = ((float) offset / LOGO_MAX_WIDTH);
        img.setAlpha(flag);
        if (flag <= 1) {
            img.setScaleX(flag);
            img.setScaleY(flag);
        }
    }

    @Override
    public void onDoState() {
        img.setAlpha(1.0f);
        img.setScaleX(1);
        img.setScaleY(1);
    }

}
