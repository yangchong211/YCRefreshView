/*
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.yczbj.ycrefreshview.type;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.cn.ycbannerlib.banner.BannerView;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.header.BannerAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter {


    public static final int TYPE_BANNER = 0;
    public static final int TYPE_AD = 1;
    public static final int TYPE_GRID = 2;
    public static final int TYPE_IMAGE = 3;
    public static final int TYPE_NEW = 4;
    private List<HomePageEntry> mData;
    private Context context;

    public HomePageAdapter(Context context) {
        this.context = context;
    }


    public void setData(List<HomePageEntry> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_BANNER:
                return new BannerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_vlayout_banner , parent, false));
            case TYPE_AD:
                return new AdViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_vlayout_ad,parent, false));
            case TYPE_GRID:
                return new GridViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_vlayout_grid,parent, false));
            case TYPE_IMAGE:
                return new ImageViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_vlayout_title,parent, false));
            case TYPE_NEW:
                return new NewViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_vlayout_news,parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_BANNER:
                // banner 逻辑处理
                setBanner((BannerViewHolder) holder,position);
                break;
            case TYPE_AD:
                // 广告逻辑处理
                setAd(holder,position);
                break;
            case TYPE_GRID:
                // 文本逻辑处理
                setGrid(holder,position);
                break;
            case TYPE_IMAGE:
                //图片逻辑处理
                setImage(holder,position);
                break;
            case TYPE_NEW:
                //视频逻辑处理
                setNew((NewViewHolder)holder,position);
                break;
            // ... 此处省去N行代码
            default:
                break;
        }
    }



    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            //banner在开头
            return TYPE_BANNER;
        }else {
            //type 的值为TYPE_AD，TYPE_IMAGE，TYPE_AD，等其中一个
            return mData.get(position).getType();
        }

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0:mData.size();
    }




    public static class BannerViewHolder extends BaseViewHolder {

        private final BannerView mBanner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            mBanner = itemView.findViewById(R.id.banner);
        }
    }

    public static class NewViewHolder extends BaseViewHolder{

        public NewViewHolder(View itemView) {
            super(itemView);
        }
    }
    public static class AdViewHolder extends BaseViewHolder{
        public AdViewHolder(View itemView) {
            super(itemView);
        }
    }
    public static class GridViewHolder extends BaseViewHolder{
        public GridViewHolder(View itemView) {
            super(itemView);
            //绑定控件
        }
    }
    public static class ImageViewHolder extends BaseViewHolder{
        public ImageViewHolder(View itemView) {
            super(itemView);
            //绑定控件
        }
    }


    private void setBanner(BannerViewHolder holder, int position) {
        // 绑定数据
        BannerView mBanner = holder.mBanner;
        mBanner.setHintGravity(1);
        mBanner.setAnimationDuration(1000);
        mBanner.setPlayDelay(2000);
        mBanner.setAdapter(new BannerAdapter(context));
    }

    private void setAd(RecyclerView.ViewHolder holder, int position) {

    }

    private void setGrid(RecyclerView.ViewHolder holder, int position) {

    }

    private void setImage(RecyclerView.ViewHolder holder, int position) {

    }

    private void setNew(NewViewHolder holder, int position) {

    }


}
