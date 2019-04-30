package org.yczbj.ycrefreshviewlib.view;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

public class InnerRecycledViewPool extends RecyclerView.RecycledViewPool {

    /**
     * destroyViewHolder
     */
    @Override
    public void clear() {
        super.clear();
    }

    /**
     * 设置丢弃前要在池中持有的视图持有人的最大数量
     * @param viewType                  type
     * @param max                       max数量
     */
    @Override
    public void setMaxRecycledViews(int viewType, int max) {
        super.setMaxRecycledViews(viewType, max);
    }

    /**
     * 返回给定视图类型的RecycledViewPool所持有的当前视图数
     * @param viewType                  type
     * @return
     */
    @Override
    public int getRecycledViewCount(int viewType) {
        return super.getRecycledViewCount(viewType);
    }

    /**
     * 从池中获取指定类型的ViewHolder，如果没有指定类型的ViewHolder，则获取{@Codenull}
     * @param viewType                  type
     * @return
     */
    @Nullable
    @Override
    public RecyclerView.ViewHolder getRecycledView(int viewType) {
        return super.getRecycledView(viewType);
    }

    /**
     * 向池中添加一个废视图保存器。如果那个ViewHolder类型的池已经满了，它将立即被丢弃。
     * @param scrap                     scrap
     */
    @Override
    public void putRecycledView(RecyclerView.ViewHolder scrap) {
        super.putRecycledView(scrap);
    }


}
