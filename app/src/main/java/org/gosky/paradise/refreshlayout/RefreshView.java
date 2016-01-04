package org.gosky.paradise.refreshlayout;

import android.view.View;

/**
 * @author galaxy captain
 * @date 2015/12/28
 */
public abstract class RefreshView {

    public abstract View createView();

    /**
     * 普通状态
     */
    public abstract void onNomarlState(int offset);

    /**
     * 可执行状态
     */
    public abstract void onExecState(int offset);

    /**
     * 加载中
     */
    public abstract void onDoState();

}
