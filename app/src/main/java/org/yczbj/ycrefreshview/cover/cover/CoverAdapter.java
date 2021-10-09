package org.yczbj.ycrefreshview.cover.cover;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.yczbj.ycrefreshview.R;

import java.util.List;


public class CoverAdapter extends RecyclerView.Adapter<CoverAdapter.ViewHolder> {

    private Context mContext;
    private List<Object> mPictureList;

    private onItemClick clickCb;

    public CoverAdapter(Context c) {
        mContext = c;
    }

    public CoverAdapter(Context c, onItemClick cb, List<Object> mPictureListt) {
        this.mPictureList = mPictureListt;
        mContext = c;
        clickCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_gallery_image_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (position >= mPictureList.size()) {
            return;
        }
        Object item = mPictureList.get(position);
        if (item instanceof Integer || item instanceof String){
            Glide.with(holder.img.getContext())
                    .asDrawable()
                    //注意，这里拉取原始图片
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .load(item)
                    .into(holder.img);
        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCb != null) {
                    clickCb.clickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPictureList==null ? 0 : mPictureList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_image);
        }
    }

    public interface onItemClick {
        void clickItem(int pos);
    }

    public void setOnClickLstn(onItemClick cb) {
        this.clickCb = cb;
    }


}
