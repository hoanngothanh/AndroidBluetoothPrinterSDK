package net.fullsnackdev.escpos.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * app初始化
 *
 * Created by liugruirong on 2017/8/3.
 */

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppInfo.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
    }
}
