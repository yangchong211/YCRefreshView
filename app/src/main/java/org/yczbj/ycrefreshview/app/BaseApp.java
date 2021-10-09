package org.yczbj.ycrefreshview.app;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;

import org.yczbj.ycrefreshview.R;

public class BaseApp extends Application {

    private static Application application;

    public static Application getApp(){
        if (application==null){
            synchronized(BaseApp.class){
                if (application==null){
                    application = new Application();
                }
            }
        }
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ViewTarget.setTagId(R.id.glide_tag);
    }


}
