package org.yczbj.ycrefreshview.staggered;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.cheoo.app.bean.BasePubuBean;

import org.greenrobot.eventbus.EventBus;
import org.yczbj.ycrefreshview.data.PictureData;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * 用于处理图片的Service
 */
public class DataService extends IntentService {

    public DataService() {
        super("");
    }

    public static void startService(Context context, ArrayList<PictureData> datas, String subtype) {
        Intent intent = new Intent(context, DataService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        intent.putExtra("subtype", subtype);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        ArrayList<PictureData> mData = intent.getParcelableArrayListExtra("data");
        String subtype = intent.getStringExtra("subtype");
        handleGirlItemData(mData, subtype);
    }

    Bitmap bitmap = null;

    private void handleGirlItemData(ArrayList<PictureData> mData, String subtype) {
        if (mData.size() == 0) {
            //EventBus.getDefault().post("finish");
            return;
        }
        for (PictureData data : mData) {
            //获取BitMap
            try {
                bitmap = Glide.with(this)
                        .load(data.getImage())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //将宽高设置到实体去
            if (bitmap != null) {
                data.setWidth(bitmap.getWidth());
                data.setHeight(bitmap.getHeight());
            }
            data.setSubtype(subtype);
        }
        //EventBus.getDefault().post(mData);
    }
}

