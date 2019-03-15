package org.yczbj.ycrefreshview.app;

import android.app.Application;

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
    }


}
