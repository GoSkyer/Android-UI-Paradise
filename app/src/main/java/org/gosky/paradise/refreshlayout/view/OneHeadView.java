package org.gosky.paradise.refreshlayout.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.gosky.paradise.R;
import org.gosky.paradise.refreshlayout.RefreshView;


/**
 * @author galaxy captain
 * @date 2016/1/3
 */
public class OneHeadView extends RefreshView {

    private Context context;

    private View view;
    private TextView normalTv;
    private TextView execTv;
    private TextView doTv;

    public OneHeadView(Context context) {
        this.context = context;
    }

    @Override
    public View createView() {
        view = LayoutInflater.from(context).inflate(R.layout.refresh_one_headview, null);
        normalTv = (TextView) view.findViewById(R.id.refresh_one_headview_normal);
        execTv = (TextView) view.findViewById(R.id.refresh_one_headview_exec);
        doTv = (TextView) view.findViewById(R.id.refresh_one_headview_do);
        return view;
    }

    @Override
    public void onNomarlState(int offset) {
        normalTv.setAlpha(1);
        execTv.setAlpha(0);
        doTv.setAlpha(0);
    }

    @Override
    public void onExecState(int offset) {
        normalTv.setAlpha(0);
        execTv.setAlpha(1);
        doTv.setAlpha(0);
    }

    @Override
    public void onDoState() {
        normalTv.setAlpha(0);
        execTv.setAlpha(0);
        doTv.setAlpha(1);
    }

}
