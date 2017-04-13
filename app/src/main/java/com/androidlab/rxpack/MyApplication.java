package com.androidlab.rxpack;

import android.app.Application;

import com.androidlab.rxpack.RxBindingTest.RxBus;

/**
 * Created by Haodong on 2017/3/12.
 */

public class MyApplication extends Application {
    private static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
        RxBus.getInstance();

    }
    public static MyApplication get_instance(){
        return INSTANCE;
    }
}
