package org.yczbj.ycrefreshview.staggered;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.PictureData;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;


public class ImageStageredAdapter extends RecyclerArrayAdapter<PictureData> {
    public ImageStageredAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(parent);
    }



    public class ImageViewHolder extends BaseViewHolder<PictureData> {
        ImageView imgPicture;

        public ImageViewHolder(ViewGroup parent) {
            super(new ImageView(parent.getContext()));
            imgPicture = (ImageView) itemView;
            imgPicture.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void setData(PictureData data) {
            ViewGroup.LayoutParams params = imgPicture.getLayoutParams();
            int setHeight = getAdapterPosition()%5;
            //计算View的高度
            int height = 300;
            switch (setHeight){
                case 0:
                    height = 500;
                    break;
                case 1:
                    height = 750;
                    break;
                case 2:
                    height = 880;
                    break;
                case 3:
                    height = 360;
                    break;
                case 4:
                    height = 660;
                    break;
                default:
                    break;
            }
            params.height = height;
            imgPicture.setLayoutParams(params);
            Glide.with(getContext())
                    .load(data.getImage())
                    .placeholder(R.drawable.bg_small_tree_min)
                    .into(imgPicture);
        }
    }
}
