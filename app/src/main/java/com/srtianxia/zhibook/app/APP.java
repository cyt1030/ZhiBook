package com.srtianxia.zhibook.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by srtianxia on 2016/1/20.
 */
public class APP extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}