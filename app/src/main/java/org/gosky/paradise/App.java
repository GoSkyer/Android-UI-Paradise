package org.gosky.paradise;

import android.app.Application;

/**
 * @author galaxy captain
 * @date 2016/1/4
 */
public class App extends Application {

    private static App instance;

    public static App getInstance() {
        if (instance == null)
            instance = new App();
        return instance;
    }

    private App() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
