package org.gosky.paradise;

import android.util.Log;

import org.gosky.paradise.base.BaseActivity;

/**
 * @author galaxy captain
 * @date 2016/1/4
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int setRootView() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initFirst() {
        super.initFirst();
        Log.e(TAG, "initFirst()");
    }

}
