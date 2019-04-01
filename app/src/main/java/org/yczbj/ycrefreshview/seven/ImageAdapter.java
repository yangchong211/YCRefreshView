package org.yczbj.ycrefreshview.seven;

import android.content.Context;
import android.view.ViewGroup;

import org.yczbj.ycrefreshview.data.PictureData;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;


public class ImageAdapter extends RecyclerArrayAdapter<PictureData> {
    public ImageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(parent);
    }
}
