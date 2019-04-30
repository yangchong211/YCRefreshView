package org.yczbj.ycrefreshviewlib.observer;

import android.support.v7.widget.RecyclerView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;

/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/4/28
 *     desc  : 自定义FixDataObserver
 *     revise: 当插入数据的时候，需要
 * </pre>
 */
public class FixDataObserver extends RecyclerView.AdapterDataObserver {

    private RecyclerView recyclerView;
    public FixDataObserver(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        if (recyclerView.getAdapter() instanceof RecyclerArrayAdapter) {
            RecyclerArrayAdapter adapter = (RecyclerArrayAdapter) recyclerView.getAdapter();
            //获取footer的数量
            int footerCount = adapter.getFooterCount();
            //获取所有item的数量，包含header和footer
            int count = adapter.getCount();
            //如果footer大于0，并且
            if (footerCount > 0 && count == itemCount) {
                recyclerView.scrollToPosition(0);
            }
        }
    }
}
