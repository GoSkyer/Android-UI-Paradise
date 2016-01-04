package org.gosky.paradise.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author galaxy captain
 * @date 2016/1/4
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static String TAG = "TAG";
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutID = this.setRootView();
        super.setContentView(layoutID);

        mContext = getApplicationContext();
        TAG = getClass().getSimpleName();

        findView();
        initView();
        initFirst();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int setRootView();

    protected abstract void findView();

    protected abstract void initView();

    protected void initFirst() {

    }

    /**
     * findViewById简洁版
     */
    protected <T extends View> T f(int id) {
        return (T) findViewById(id);
    }

}
