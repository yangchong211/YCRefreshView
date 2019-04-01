package org.yczbj.ycrefreshview.second;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;


public class NarrowImageAdapter extends RecyclerArrayAdapter<Integer> {


    public NarrowImageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NarrowImageViewHolder(parent);
    }

    private static class NarrowImageViewHolder extends BaseViewHolder<Integer>{
        ImageView imgPicture;

        NarrowImageViewHolder(ViewGroup parent) {
            super(new ImageView(parent.getContext()));
            imgPicture = (ImageView) itemView;
            imgPicture.setLayoutParams(new ViewGroup.LayoutParams((int)
                    AppUtils.convertDpToPixel(80.0f,getContext()),
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void setData(Integer data) {
            imgPicture.setImageResource(data);
        }
    }
}
