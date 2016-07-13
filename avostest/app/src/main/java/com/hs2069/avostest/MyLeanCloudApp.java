package com.hs2069.avostest;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by edward2414 on 7/12/2016.
 */
public class MyLeanCloudApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "K0azWb0067vg2P46h1waNCtg-gzGzoHsz", "am6l8j6JEYHSpKVTM112qwNl");
    }
}
