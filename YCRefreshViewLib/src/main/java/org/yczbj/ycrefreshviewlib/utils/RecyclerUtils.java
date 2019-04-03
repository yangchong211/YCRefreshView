package org.yczbj.ycrefreshviewlib.utils;


import android.app.Application;
import android.content.Context;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/4/22
 *     desc  : 工具类
 *     revise:
 * </pre>
 */
public final class RecyclerUtils {

    public static void checkContent(Context context){
        if (context==null){
            throw new NullPointerException("context is not null");
        }
        if (context instanceof Application){
            throw new UnsupportedOperationException("context is not application");
        }
    }

}
