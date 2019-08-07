package org.yczbj.ycrefreshview.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.lang.reflect.Field;

public class SysUtils {

	public static int Dp2Px(Context context, float dp) {
		if (context==null){
			//避免空指针异常
			context = BaseApp.getApp();
		}
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static int getScreenWidth(Activity activity){
		int width = 0;
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		width=display.getWidth();
		return width;
	}




}
